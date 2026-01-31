package viewGUI;


import java.net.URL;
import java.util.ResourceBundle;

import controller.Service;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import main.Interpreter;
import model.program_state.*;
import model.statement.IStmt;
import my_exceptions.MyException;
import my_exceptions.TypeException;
import repository.IRepository;
import repository.Repository;

public class ProgramSelectorController implements Initializable {
    private static int count=0;

    @FXML
    private Button runButton;
    @FXML
    private TextArea info;
    @FXML
    private ListView<IStmt> programs;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setInitialContent();
        connectEvents();
    }

    private void setInitialContent(){
        programs.getItems().setAll(Interpreter.getAllPrograms());

        info.setWrapText(true);
        info.setPrefRowCount(1);

    }
    private void connectEvents(){
        runButton.setOnAction(event -> {
            IStmt statement=programs.getSelectionModel().getSelectedItem();
            info.clear();
            if(statement==null){return;}
            try {
                statement.typecheck(new TypeEnv());
                Service service =createService(statement);
                loadMainWindow(service);
            }
            catch(TypeException e) {
                //System.out.println("Type error: "+e.getMessage());//DEBUG
                info.setText("Type error: "+e.getMessage());
                info.setStyle("-fx-text-fill: red;");

            }
            catch(MyException e) {
                info.setText("NONONO: "+e.getMessage());
                info.setStyle("-fx-text-fill: red;");

            }
        });
        info.textProperty().addListener((obs, oldText, newText) -> {
            info.setPrefHeight(
                    info.lookup(".text").getBoundsInLocal().getHeight() + 40
            );
        });

    }

    ///this statement is supposed to pass the typechecker
    private Service createService(IStmt statement){
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

    private void loadMainWindow(Service service){
        try {
            // LOAD SECOND WINDOW
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main_window.fxml"));


            Parent root = loader.load();

            MainWindowController controller = loader.getController();
            controller.setService(service);
            controller.firstLoad();

            Stage stage = new Stage();
            stage.setTitle("Program");

            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/my_style.css").toExternalForm());

            stage.setScene(scene);
            stage.show();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}