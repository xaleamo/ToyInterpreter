package controller;

import model.program_state.PrgState;
import model.value.RefValue;
import model.value.Value;
import my_exceptions.MyException;
import repository.IRepository;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class Service {
    ExecutorService executor;
    IRepository repo;
    boolean displayFlag=false;

    public Service(IRepository repo) {
        this.repo = repo;

    }

    public void setDisplayFlag(boolean b) {displayFlag = b;}


    /**
     * Executes the statement at the top of the <i>ExecutionStack</i> of <i>state</i>.
     * @throws my_exceptions.ProgramStateException if ExecutionStack is empty.
     */


    public void printProgramState(PrgState state){
        System.out.println(state.toString("color"));
    }


    public void oneStepForAllPrg(List<PrgState> prgList) throws MyException{
        prgList.forEach(ps->repo.logPrgStateExec(ps));
        List<Callable<PrgState>> callList = prgList.stream()
                .map(p->(Callable<PrgState>)()->{return p.oneStep();})
                .collect(Collectors.toList());

        //get newly created PrgStates =new  threads

        try{
            List<PrgState> newPrgList = executor.invokeAll(callList).stream()
                    .map(future -> {
                        try {
                            return future.get();
                        } catch (ExecutionException e) {
                            if (e.getCause() instanceof MyException) {
                                throw (MyException) e.getCause();
                            } else {
                                throw new MyException("This should not have happened: " + e.getMessage());
                            }
                        } catch (InterruptedException e) {
                            throw new MyException("Thread was interrupted.");
                        } catch (CancellationException e) {
                            throw new MyException("Thread was cancelleds.");
                        }
                    })
                    .filter(p -> p != null)
                    .collect(Collectors.toList());
            prgList.addAll(newPrgList);
            prgList.forEach(p->repo.logPrgStateExec(p));//log in file

            //Save the current programs in the repository
            repo.setPrgList(prgList);
        }catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new MyException("Execution interrupted"+ e.getMessage());
        }

    }

    public void allStep(){
        //printProgramState(repo.getPrgList().getFirst());
        executor = Executors.newFixedThreadPool(2);
        //remove completed programs
        List<PrgState> prgList=removeCompletedPrg(repo.getPrgList());//this ensures PrgState.oneStep() won't throw an error when calling oneStepForAll()

        while(!prgList.isEmpty()){
            Set<Value> used= prgList.stream()
                    .map(p-> new HashSet<>(p.getSymTable().getContent().values()))
                    .reduce(new HashSet<>(),(a,b)->{a.addAll(b);return a;});
            safeGarbageCollector(getAllUsedAddresses(used,prgList.getFirst().getHeap().getContent()), prgList.getFirst().getHeap().getContent());

            oneStepForAllPrg(prgList);//this calls repo.setPrgState(prgList)//is called on prgStates that allow at least one more step
            prgList=removeCompletedPrg(repo.getPrgList());
        }
        executor.shutdownNow();
        for(PrgState prgState:repo.getPrgList()){
            printProgramState(prgState);
        }
        repo.setPrgList(prgList);


    }



    public List<PrgState> removeCompletedPrg(List<PrgState> inPrgList){
        return inPrgList.stream()
                .filter(s->s.isNotCompleted())
                .collect(Collectors.toList());
    }











    //garbage collector implementation
    //de ce asa intortocheat??????

    List<Integer> getAddrFromSymTable(Collection<Value> symTableValues){
        return symTableValues.stream()
                .filter(v-> v instanceof RefValue)
                .map(v-> {RefValue v1 = (RefValue)v; return v1.getAddress();})
                .collect(Collectors.toList());
    }

    Set<Integer> getAllUsedAddresses(Collection<Value> symTableValues, Map<Integer,Value> heap){
        List<Integer> symAddr=getAddrFromSymTable(symTableValues);
        Set<Integer> usedAddresses=new HashSet<>();
        for(Integer e:symAddr){
            //heap: aux->v
            int aux=e;
            Value v;
            while(aux != 0) {
                //if(usedAddresses.contains(aux))//NOT OK  manage differently used check
                    //throw new MyException("Err? Circular addressing"); //never bcs we have no cast
                usedAddresses.add(aux);
                v=heap.get(aux);
                //may be null-> dangling reference
                if(v==null)
                    throw new MyException("Err dangling reference found with garbage colletor");
                if(v instanceof RefValue){
                    aux=((RefValue)v).getAddress();
                }
                else aux=0;
            }
        }
        return usedAddresses;
    }

    Map<Integer, Value> unsafeGarbageCollector(List<Integer> symAddr, Map<Integer,Value> heap){
        return heap.entrySet().stream().filter(e->symAddr.contains(e.getKey())).collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue));
    }
    Map<Integer, Value> safeGarbageCollector(Set<Integer> usedAddr, Map<Integer,Value> heap){
        return heap.entrySet().stream().filter(e->usedAddr.contains(e.getKey())).collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue));
    }



    //unsafe
    public void wrapperUnsafeGarbageCollector(PrgState ps){
        ps.getHeap().setContent(unsafeGarbageCollector( getAddrFromSymTable(ps.getSymTable().getContent().values()), ps.getHeap().getContent()));
    }
    //safe
    public void wrapperSafeGarbageCollector(PrgState ps){
        ps.getHeap().setContent(safeGarbageCollector( getAllUsedAddresses(ps.getSymTable().getContent().values(), ps.getHeap().getContent()), ps.getHeap().getContent()));
    }

    //------------------------------------
}
