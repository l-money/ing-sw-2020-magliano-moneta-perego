package santorini.view;

import javafx.fxml.FXML;
import santorini.model.Cell;
import santorini.model.Mossa;
import santorini.model.Table;
import santorini.model.godCards.God;

import java.util.ArrayList;

public class ViewController extends View {


    public ViewController() {

    }

    @FXML
    public void initialize() {

    }


    @Override
    public void chooseCards(ArrayList<God> gods) {
    }

    @Override
    public void setNewAction(Mossa.Action action) {

    }

    @Override
    public void setNumeroGiocatori() {
    }

    @Override
    public void setFailed(String msg) {

    }

    @Override
    public void printMessage(String msg) {

    }

    @Override
    public void setInitializePawn() {

    }

    @Override
    public void vittoria() {

    }

    @Override
    public void sconfitta(String winner) {

    }

    @Override
    public void networkError(String player) {

    }

    @Override
    public void printTable() {

    }

    /**
     * method selectionCardGUI
     *
     * @param gods the gods choose by the extraction for the game
     */
    public void selectionCardGUI(ArrayList<God> gods /**scena dove ci sono le carte*/) {
        //*** = nella scena i-esima metto l'immagine di quella carta
        int l = gods.size();
        for (int i = 0; i < l; i++) {
            String s = gods.get(i).getName();
            switch (s) {
                case "Apollo":
                    //nella scena nella posizione i-esima metto l'immagine di Apollo
                    break;
                case "Apres":
                    //***
                    break;
                case "Artemis":
                    //***
                    break;
                case "Athena":
                    //***
                    break;
                case "Atlas":
                    //***
                    break;
                case "Chronus":
                    //***
                    break;
                case "Demeter":
                    //***
                    break;
                case "Hephaestus":
                    //***
                    break;
                case "Hestia":
                    //***
                    break;
                case "Minotaur":
                    //***
                    break;
                case "Pan":
                    //***
                    break;
                case "Pdor":
                    //***
                    break;
                case "Prometheus":
                    //***
                    break;
                case "Triton":
                    //***
                    break;
                case "Zeus":
                    //***
                    break;
                default:
                    //metto immagine vuota
                    break;
            }
        }
    }

//AGGIORNAMENTO TABLE = QUESTA MERDA LOOL :P

    public void updateTableGUI(Table t /**, griglia della gui 5x5*/) {
        // ### = ritorno immagine livello = x, giocatore (colore) = y, pedina = z
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Cell cell = t.getTableCell(i, j);
                //casella della gtiglia (i,j)
                switch (cell.getLevel()) {
                    case 0:
                        if (cell.getPawn() != null) {
                            if (cell.getPawn().getIdGamer() == 0 && cell.getPawn().getIdPawn() == 0) {
                                //x=0,y=giallo,z=0
                            } else if (cell.getPawn().getIdGamer() == 0 && cell.getPawn().getIdGamer() == 1) {
                                //x=0,y=giallo,z=1
                            } else if (cell.getPawn().getIdGamer() == 1 && cell.getPawn().getIdPawn() == 0) {
                                //x=0,y=rosso,z=0
                            } else if (cell.getPawn().getIdGamer() == 1 && cell.getPawn().getIdGamer() == 1) {
                                //x=0,y=rosso,z=1
                            } else if (cell.getPawn().getIdGamer() == 2 && cell.getPawn().getIdPawn() == 0) {
                                //x=0,y=blu,z=0
                            } else if (cell.getPawn().getIdGamer() == 2 && cell.getPawn().getIdGamer() == 1) {
                                //x=0,y=blu,z=1
                            }
                        } else if (cell.isComplete()) {
                            //l=0 + dome
                        } else {
                            //no image
                        }
                        break;
                    case 1:
                        if (cell.getPawn() != null) {
                            if (cell.getPawn().getIdGamer() == 0 && cell.getPawn().getIdPawn() == 0) {
                                //x=1,y=giallo,z=0
                            } else if (cell.getPawn().getIdGamer() == 0 && cell.getPawn().getIdGamer() == 1) {
                                //x=1,y=giallo,z=1
                            } else if (cell.getPawn().getIdGamer() == 1 && cell.getPawn().getIdPawn() == 0) {
                                //x=1,y=rosso,z=0
                            } else if (cell.getPawn().getIdGamer() == 1 && cell.getPawn().getIdGamer() == 1) {
                                //x=1,y=rosso,z=1
                            } else if (cell.getPawn().getIdGamer() == 2 && cell.getPawn().getIdPawn() == 0) {
                                //x=1,y=blu,z=0
                            } else if (cell.getPawn().getIdGamer() == 2 && cell.getPawn().getIdGamer() == 1) {
                                //x=1,y=blu,z=1
                            }
                        } else if (cell.isComplete()) {
                            //l=1 + dome
                        } else {
                            //l=1
                        }
                        break;
                    case 2:
                        if (cell.getPawn() != null) {
                            if (cell.getPawn().getIdGamer() == 0 && cell.getPawn().getIdPawn() == 0) {
                                //x=2,y=giallo,z=0
                            } else if (cell.getPawn().getIdGamer() == 0 && cell.getPawn().getIdGamer() == 1) {
                                //x=2,y=giallo,z=1
                            } else if (cell.getPawn().getIdGamer() == 1 && cell.getPawn().getIdPawn() == 0) {
                                //x=2,y=rosso,z=0
                            } else if (cell.getPawn().getIdGamer() == 1 && cell.getPawn().getIdGamer() == 1) {
                                //x=2,y=rosso,z=1
                            } else if (cell.getPawn().getIdGamer() == 2 && cell.getPawn().getIdPawn() == 0) {
                                //x=2,y=blu,z=0
                            } else if (cell.getPawn().getIdGamer() == 2 && cell.getPawn().getIdGamer() == 1) {
                                //x=2,y=blu,z=1
                            }
                        } else if (cell.isComplete()) {
                            //l=2 + dome
                        } else {
                            //l=2
                        }
                        break;
                    case 3:
                        if (cell.getPawn() != null) {
                            if (cell.getPawn().getIdGamer() == 0 && cell.getPawn().getIdPawn() == 0) {
                                //x=3,y=giallo,z=0
                            } else if (cell.getPawn().getIdGamer() == 0 && cell.getPawn().getIdGamer() == 1) {
                                //x=3,y=giallo,z=1
                            } else if (cell.getPawn().getIdGamer() == 1 && cell.getPawn().getIdPawn() == 0) {
                                //x=3,y=rosso,z=0
                            } else if (cell.getPawn().getIdGamer() == 1 && cell.getPawn().getIdGamer() == 1) {
                                //x=3,y=rosso,z=1
                            } else if (cell.getPawn().getIdGamer() == 2 && cell.getPawn().getIdPawn() == 0) {
                                //x=3,y=blu,z=0
                            } else if (cell.getPawn().getIdGamer() == 2 && cell.getPawn().getIdGamer() == 1) {
                                //x=3,y=blu,z=1
                            }
                        } else if (cell.isComplete()) {
                            //l=3 + dome = complete
                        } else {
                            //l=3
                        }
                        break;
                    default:
                        break;
                }
            }
        }
    }



}
