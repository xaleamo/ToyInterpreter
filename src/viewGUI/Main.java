package viewGUI;

import controller.Service;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.Interpreter;
import model.program_state.*;
import model.statement.Statement;
import model.value.Id;
import model.value.StringValue;
import model.value.Value;
import my_exceptions.MyException;
import my_exceptions.TypeException;
import repository.IRepository;
import repository.Repository;



import java.util.Map;
import java.util.stream.Collectors;

public class Main extends Application {
    private int count=0;
    @Override
    public void start(Stage mainStage) throws Exception {
        //FXMLLoader loader = new FXMLLoader(getClass().getResource("program_selector.fxml"));
        //ui layout etc
        HBox main_layout=new HBox();//aka the root
        ListView<Statement> programs=new ListView<>();
        Button run=new Button("Run");
        TextField info=new TextField();
        info.setEditable(false);

        main_layout.getChildren().addAll(programs,run,info);

        Scene scene = new Scene(main_layout,500,800);
        mainStage.setScene(scene);
        mainStage.setTitle("My App");
        mainStage.show();

        //ui management
        programs.getItems().addAll(Interpreter.getAllPrograms());
                //.stream()
                //.map(e->e.toString())
                //.collect(Collectors.toCollection(ArrayList::new)));

        run.setOnAction(event -> {
            Statement statement=programs.getSelectionModel().getSelectedItem();
            info.clear();
            if(statement==null){return;}
            try {
                statement.typecheck(new TypeEnv());
                Service service =createService(statement);
                MainWindow mainWindow=new MainWindow(service);
                mainWindow.open();
            }
            catch(TypeException e) {
                //System.out.println("Type error: "+e.getMessage());//DEBUG
                info.setText("Type error: "+e.getMessage());
                info.setStyle("-fx-text-fill: red;");

            }
            catch(MyException e) {
                info.setText("Type error: "+e.getMessage());
                info.setStyle("-fx-text-fill: red;");

            }

        });



    }
    ///this statement is supposed to pass the typechecker
    private Service createService(Statement statement){
        //construct program state, repo and service dynamically
        ExecutionStack executionStack = new ExecutionStack();
        executionStack.push(statement);
        PrgState prg=new PrgState(executionStack,new SymTable(), new Heap(), new Output(), new FileTable());

        String filepath="files/log";

        filepath+=Integer.toString(++count)+".txt";
        //filepath+='('+count+')'+".txt";

        IRepository repo=new Repository(prg,filepath);
        Service service=new Service(repo);
        return service;
    }


    public static void main(String[] args) {
        launch();
    }
}

class SelectionWindow{


}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
class MainWindow{
    private Service service;

    // UI widgets as class fields
    private TextField nrOfPrgStates;
    private TableView<Map.Entry<Integer,Value>> heapTable;
    private ListView<Value> output;
    private ListView<StringValue> fileTable;
    private ListView<Integer> prgStateIDs;
    private TableView<Map.Entry<Id,Value>> symTable;
    private ListView<Statement> executionStack;
    private Button runOneStepButton;
    private TextArea errorLogArea;

    public MainWindow(Service service) {
        this.service = service;
    }

    public void open(){
        Stage stage = new Stage();

        BorderPane root = new BorderPane();

        nrOfPrgStates = new TextField();
        nrOfPrgStates.setEditable(false);

        heapTable = new TableView<>();
        output = new ListView<>();
        fileTable = new ListView<>();
        prgStateIDs = new ListView<>();
        symTable = new TableView<>();
        executionStack = new ListView<>();
        runOneStepButton = new Button("Run one step");
        errorLogArea = new TextArea();
        errorLogArea.setEditable(false);
        errorLogArea.setWrapText(true);
        errorLogArea.setStyle("-fx-text-fill: red;");

        //----------------------------------------------------------------------------------------------------------
        //layout
        Label nrOfPrgStatesLabel = new Label("Prg States:");

        VBox executionStackWidget=new VBox(new Label("Execution stack:"),executionStack);
        VBox heapTableWidget = new VBox(new Label("Heap"), heapTable);
        VBox symTableWidget = new VBox(new Label("SymTable"), symTable);
        VBox fileTableWidget = new VBox(new Label("FileTable"), fileTable);
        VBox outputWidget = new VBox(new Label("Output"), output);
        VBox prgStateWidget = new VBox(new Label("Prg States"), prgStateIDs);

        root.setTop(new HBox(nrOfPrgStatesLabel,nrOfPrgStates,runOneStepButton));
        root.setCenter(executionStackWidget);
        root.setRight(new HBox(heapTableWidget,symTableWidget,fileTableWidget));
        root.setLeft(prgStateWidget);
        root.setBottom(new HBox(outputWidget,new VBox(new Label("Error msg"),errorLogArea)));

        //----------------------------------------------------------------------------------------------------------

        TableColumn<Map.Entry<Integer, Value>, Integer> addressColHeap = new TableColumn<>("Address");
        addressColHeap.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getKey())
        );
        TableColumn<Map.Entry<Integer, Value>, Value> valueCol = new TableColumn<>("Value");
        valueCol.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getValue())
        );
        heapTable.getColumns().setAll(addressColHeap, valueCol);


        TableColumn<Map.Entry<Id, Value>, Id> idColSym = new TableColumn<>("Id");
        idColSym.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getKey())
        );
        TableColumn<Map.Entry<Id, Value>, Value> valueColSym = new TableColumn<>("Value");
        valueColSym.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getValue())
        );

        symTable.getColumns().setAll(idColSym, valueColSym);
        //----------------------------------------------------------------------------------------------------------


        Scene scene=new Scene(root,700,700);
        stage.setTitle("My App");
        stage.setScene(scene);
        stage.show();


        //ui handling

        prgStateIDs.getItems().setAll(service.getPrgStates().stream().map(e->e.getId()).collect(Collectors.toSet()));
        nrOfPrgStates.setText(Integer.toString(service.getPrgStates().size()));
        runOneStepButton.setOnAction(event -> {

            try {
                service.runOneStep();
            }
            catch (MyException e) {
                errorLogArea.setText(e.getMessage());
                runOneStepButton.setDisable(true);
            }
            if(prgStateIDs.getSelectionModel().getSelectedItem()==null) {
                prgStateIDs.getSelectionModel().select(0);
            }

            if(service.getPrgStates().size()==0){
                errorLogArea.setStyle("-fx-text-fill: green;");
                errorLogArea.setText("Execution successful.");
                runOneStepButton.setDisable(true);
            }
            else
                update(prgStateIDs.getSelectionModel().getSelectedItem());



        });


        prgStateIDs.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if(newValue!=null){
                        PrgState prgState=getPrgState(newValue);
                        updateSymTable(prgState);
                        updateExecutionStack(prgState);
                    }
                });

    }

    public void update(Integer id){
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