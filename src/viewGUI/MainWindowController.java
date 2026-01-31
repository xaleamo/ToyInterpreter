package viewGUI;

import controller.Service;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.beans.property.ReadOnlyObjectWrapper;
import model.program_state.PrgState;
import model.statement.IStmt;
import model.value.*;
import my_exceptions.MyException;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class MainWindowController implements Initializable {

    private Service service;
    public void setService(Service service) {
        this.service = service;
    }


    // UI widgets
    @FXML private TextField nrOfPrgStates;
    @FXML private TableView<Map.Entry<Integer, Value>> heapTable;
    @FXML private ListView<Value> output;
    @FXML private ListView<StringValue> fileTable;
    @FXML private ListView<Integer> prgStateIDs;
    @FXML private TableView<Map.Entry<Id, Value>> symTable;
    @FXML private ListView<IStmt> executionStack;
    @FXML private Button runOneStepButton;
    @FXML private TextArea errorLogArea;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        initializeTables();
        connectEvents();
    }

    public void firstLoad(){
        setInitialContent();//depends on service
    }
    private void setInitialContent(){
        prgStateIDs.getItems().setAll(
                service.getPrgStates()
                        .stream()
                        .map(PrgState::getId)
                        .collect(Collectors.toSet())
        );

        nrOfPrgStates.setText(
                String.valueOf(service.getPrgStates().size())
        );
    }

    private void initializeTables(){
        // Heap table columns
        TableColumn<Map.Entry<Integer, Value>, Integer> addressColHeap =
                new TableColumn<>("Address");
        addressColHeap.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getKey())
        );

        TableColumn<Map.Entry<Integer, Value>, Value> valueColHeap =
                new TableColumn<>("Value");
        valueColHeap.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getValue())
        );

        heapTable.getColumns().setAll(addressColHeap, valueColHeap);

        // SymTable columns
        TableColumn<Map.Entry<Id, Value>, Id> idColSym =
                new TableColumn<>("Id");
        idColSym.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getKey())
        );

        TableColumn<Map.Entry<Id, Value>, Value> valueColSym =
                new TableColumn<>("Value");
        valueColSym.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getValue())
        );

        symTable.getColumns().setAll(idColSym, valueColSym);
    }
    private void connectEvents(){
        // Selection listener
        //if sth new is selected -> update relevant tables
        prgStateIDs.getSelectionModel().selectedItemProperty()
                .addListener((obs, oldVal, newVal) -> {
                    if (newVal != null) {
                        PrgState prgState = getPrgState(newVal);
                        updateSymTable(prgState);
                        updateExecutionStack(prgState);
                    }
                });

        runOneStepButton.setOnAction(event -> {
            //error bcs run then get selected model, without updating selection list
            try {
                service.runOneStep();
            }
            catch (MyException e) {
                errorLogArea.setText(e.getMessage());
                runOneStepButton.setDisable(true);
            }

            updatePrgStateIds();
            if(prgStateIDs.getSelectionModel().getSelectedItem()==null) {
                prgStateIDs.getSelectionModel().select(0);
            }

            if(service.getPrgStates().isEmpty()){
                errorLogArea.setStyle("-fx-text-fill: green;");
                errorLogArea.setText("Execution successful.");
                runOneStepButton.setDisable(true);
            }
            else
                update(prgStateIDs.getSelectionModel().getSelectedItem());



        });
    }



    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /// AUXILIARY FUNCTIONS///
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void update(Integer id){
        nrOfPrgStates.setText(Integer.toString(service.getPrgStates().size()));
        PrgState prgState=getPrgState(id);
        if(prgState==null){throw new MyException("invalid prg state id. Called update wrongly.");}

        updateHeapTable(prgState);
        updateOutput(prgState);
        updateFileTable(prgState);
        updatePrgStateIds();
        updateSymTable(prgState);
        updateExecutionStack(prgState);
    }

    private PrgState getPrgState(Integer id){
        return service.getPrgStates().stream()
                .filter(prg->prg.getId()==id)
                .findFirst().orElse(null);
    }

    private void updateHeapTable(PrgState prgState){
        heapTable.getItems().setAll(prgState.getHeap().getContent().entrySet());
    }
    private void updateOutput(PrgState prgState){
        output.getItems().setAll(prgState.getOutput().getContent());
    }
    private void updateFileTable(PrgState prgState){
        fileTable.getItems().setAll(prgState.getFileTable().getContent().keySet());
    }
    private void updatePrgStateIds(){
        prgStateIDs.getItems().setAll(service.getPrgStates().stream().map(e->e.getId()).collect(Collectors.toSet()));
    }
    private void updateSymTable(PrgState prgState){
        symTable.getItems().setAll(prgState.getSymTable().getContent().entrySet());
    }
    private void updateExecutionStack(PrgState prgState){
        executionStack.getItems().setAll(prgState.getExecutionStack().getContent());
    }
}

