/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package elect1ammad;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;

/**
 *
 * @author Mr_Abdelhake
 */
public class FXMLDocumentController implements Initializable {

    Opperation opp = new Opperation();
    Parametre parametre = new Parametre();
    Variable var = new Variable();

    @FXML
    private TableView<MatrixElement> table;
    @FXML
    private RadioButton rb1, rb2, rb3, rb4;
    @FXML
    private AnchorPane newAncor;
    @FXML
    private AnchorPane oppAncor;
    @FXML
    private TextField cText, dText, poidText, nameText;
    @FXML
    private TextArea matText, calc, tree;
    @FXML
    private HBox hbt, hba;
    String path = "";
    boolean isPerf = true;
    boolean isShow = true;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        String path = fc.showOpenDialog(null).getPath();
        path = "src\\data\\input.csv";
        List l = opp.readCSV(path);
        dataSeter(l);
        showData();
    }

    public void clearTable() {
        table.getItems().clear();
        table.getColumns().clear();
    }

    public void showData() {

        clearTable();
        double[][] mat = getMatrix();
        if (mat != null) {
            // Create columns dynamically based on matrix dimensions
            for (int i = 0; i < mat[0].length + 1; i++) {
                TableColumn<MatrixElement, String> column;
                if (i == 0) {
                    column = new TableColumn<>("");
                } else {
                    if (isPerf) {
                        column = new TableColumn<>("C" + (i));
                    } else {
                        column = new TableColumn<>("P" + (i));
                    }
                }

                final int columnIndex = i;
                column.setCellValueFactory(cellData -> cellData.getValue().getValue(columnIndex));
                table.getColumns().add(column);
            }
            // Populate the table with data from the matrix
            for (int row = 0; row < mat.length; row++) {
                table.getItems().add(new MatrixElement(opp.convertDoubleMatrixToString(mat)[row]));
            }
            table.refresh();

        }

    }

    public double[][] getMatrix() {
        hbt.setVisible(true);
        hba.setVisible(false);
        isShow = true;
        if (rb1.isSelected()) {
            isPerf = true;
            return parametre.getMPerf();

        } else {
            isPerf = false;
            if (rb2.isSelected()) {
                return var.getCorcandance();
            } else {
                if (rb3.isSelected()) {
                    return var.getDiscordance();
                } else {
                    if (rb4.isSelected()) {
                        return var.getSurclassment();
                    } else {
                        hbt.setVisible(false);
                        opp.sommeDeUn(var.getSurclassment(), tree);
                        hba.setVisible(true);
                        isShow = false;
                        return null;
                    }

                }
            }
        }
    }

    public void openFile() {
        FileChooser fc = new FileChooser();
        fc.setInitialDirectory(new File("C:\\Users\\Mr_Abdelhake\\Documents\\NetBeansProjects\\Elect1Ammad\\src\\data"));
        path = fc.showOpenDialog(null).getPath();
        System.out.println(path);
        List l = opp.readCSV(path);
        dataSeter(l);
        showData();
    }

    public void dataSeter(List l) {
        parametre = opp.creatMatrice(l);
        var.setCorcandance(opp.creatMCorcondance(parametre.getMPerf(), parametre.getPoid()));
        var.setDiscordance(opp.creatMdiscordance(parametre.getMPerf(), calc));
        var.setSurclassment(opp.creatMsurclassement(var.getCorcandance(), var.getDiscordance(), parametre.getD(), parametre.getC()));
        showData();
    }

    public void newMat() throws IOException {
        String textCSV = "";

        textCSV += cText.getText() + "\n";
        textCSV += dText.getText() + "\n";
        textCSV += poidText.getText() + "\n";

        String[] values = matText.getText().replace("\n", "").split(";");

        for (String value : values) {
            textCSV += value + "\n";
        }
        opp.creatCSVFile(textCSV, nameText.getText());
        goBack();
        path = "src\\data\\" + nameText.getText() + ".csv";

        List l = opp.readCSV(path);
        rb1.setSelected(true);
        dataSeter(l);
        showData();

    }

    public void changeWindow() {
        newAncor.setVisible(true);
        oppAncor.setVisible(false);
    }

    public void goBack() {
        newAncor.setVisible(false);
        oppAncor.setVisible(true);
    }

    public static class MatrixElement {

        private final SimpleStringProperty[] values;

        public MatrixElement(String[] row) {
            values = new SimpleStringProperty[row.length];
            for (int i = 0; i < row.length; i++) {
                values[i] = new SimpleStringProperty(row[i]);
            }
        }

        public SimpleStringProperty getValue(int index) {
            return values[index];
        }
    }

}
