package viewGUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage mainStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/program_selector.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root,500,800);
        scene.getStylesheets().add(getClass().getResource("/my_style.css").toExternalForm());

        mainStage.setScene(scene);
        mainStage.setTitle("My App");
        mainStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/*class MainWindow{
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
}*/