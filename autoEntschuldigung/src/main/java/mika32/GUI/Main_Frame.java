package mika32.GUI;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import com.toedter.calendar.*;
import mika32.Main;
import mika32.Save.save;
import mika32.Utils.PathValidation;

public class Main_Frame {
    JFrame frame;
    JCheckBox standartWerte;
    JPanel menu_Panel;
    JPanel pan_Name;
    JPanel mainPanel;
    JDateChooser  startDatumAuswahl;
    JDateChooser endDatumAuswahl;
    JTextField box_name;
    JTextField speichernField;
    JLabel straßeLabel;
    JTextField straßeField;
    JLabel ortLabel;
    JTextField ortField;
    JToggleButton zeitraum;
    JToggleButton datum;
    Font smallFont = new Font("Arial", Font.BOLD,20);
    Font bigFont = new Font("Impact", Font.BOLD, 23);
    Color greyBack = new Color(213, 213, 213);
    Color greyForg = new Color(229, 228, 228);
    Color purpleBack = new Color(81, 8, 157);
    Color menuPanelBack = new Color(39, 4, 76);
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");


    public Main_Frame(){
        frame = new JFrame();
        pan_Name = new JPanel();
        menu_Panel = new JPanel();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(750, 650);
        frame.setLayout(new BorderLayout());

        frame.setLocationRelativeTo(null);
        frame.setTitle("autoEntschuldigung v1.1");
        frame.setResizable(false);
        mainPanel();
        menuPanel();
    }

    private void mainPanel(){
        mainPanel = new JPanel();

        mainPanel.setPreferredSize(new Dimension(500, frame.getHeight()));
        mainPanel.setLayout(null);
        mainPanel.setBackground(greyBack);

        titel_mainPanel();
        titelSmall_mainPanel();
        txtB_name();
        datumAuswahl();
        eingabeDatum();
        txtB_speichern();
        addresseBox();
        standartWerte_Check();
        generierenButton();
    }

    private void menuPanel(){
        menu_Panel.setLayout(null);
        menu_Panel.setPreferredSize(new Dimension(250, 300));
        menu_Panel.setBackground(menuPanelBack);

        titel();
        picto_title();
        filler();
    }

    public void frame_done(){
        frame.getContentPane().add(menu_Panel, BorderLayout.WEST);
        frame.getContentPane().add(mainPanel, BorderLayout.EAST);
        frame.getContentPane().add(pan_Name, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private void generierenButton(){
        JButton generierenButton = new JButton();

        generierenButton.setBounds(150, 560, 200, 50);
        generierenButton.setFocusPainted(false);

        generierenButton.setBackground(purpleBack);
        generierenButton.setForeground(greyForg);

        generierenButton.setFont(smallFont);
        generierenButton.setText("Generieren");

        generierenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int d = Main.student_name.length();
                String leerzeichen = "";

                for(int i = d; i < 123; i++){
                    leerzeichen = leerzeichen + " ";
                }

                System.out.println("Name länge " + box_name.getText().length());
                System.out.println("Leerzeichen länge " + leerzeichen.length());

                Main.student_name = box_name.getText() + leerzeichen;
                Main.plzOrt = ortField.getText();
                Main.straßeNr = straßeField.getText();
                Main.safeCon = standartWerte.isSelected();

                save.setStudentname(box_name.getText());
                save.setOrtPlz(ortField.getText());
                save.setStraßeHsNr(straßeField.getText());

                try {
                    if(PathValidation.test(speichernField.getText())){
                        Main.pathToSave = speichernField.getText() + "/";
                        save.setSpeicherPath(speichernField.getText());
                    }else {
                        System.out.println("In den else beim pfard gefallen!");
                        Main.pathToSave = "/Users/mikadobrowolski/Desktop";
                        save.setSpeicherPath(Main.pathToSave);
                    }

                    if(datum.isSelected()){
                        Main.einzelnesDatum = true;
                        Main.date = dateFormat.format(startDatumAuswahl.getDate());

                    }else {
                        Main.einzelnesDatum = false;
                        getMultiDates(startDatumAuswahl.getDate(), endDatumAuswahl.getDate());
                    }

                    Main.run();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        mainPanel.add(generierenButton);
    }

    private void getMultiDates(Date startDatum, Date endDatum){
        ArrayList<LocalDate> dates_withWeekend = new ArrayList<>();
        LocalDate startDatum_loc;
        LocalDate endDatum_loc;

        startDatum_loc = startDatum.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        endDatum_loc = endDatum.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        LocalDate date = startDatum_loc;

        while(!date.isAfter(endDatum_loc)){
            dates_withWeekend.add(date);
            date = date.plusDays(1);
        }

        System.out.println(dates_withWeekend);
        delWeekend(dates_withWeekend);
    }

    private static void delWeekend(ArrayList<LocalDate> dates_mitWochenende){
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        Iterator<LocalDate> iterator = dates_mitWochenende.iterator();

        while (iterator.hasNext()) {
            LocalDate date = iterator.next();
            DayOfWeek dayOfWeek = date.getDayOfWeek();
            if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
                iterator.remove(); // Entfernen Sie das aktuelle Datum
            }
        }

        for (LocalDate date : dates_mitWochenende) {
            Main.dates.add(date.format(format));
        }
    }

    private void standartWerte_Check(){
        standartWerte = new JCheckBox();

        standartWerte.setBounds(60, 510, 400, 40);
        standartWerte.setVisible(true);

        standartWerte.setFont(smallFont);
        standartWerte.setText("Werte als Standart speichern");
        standartWerte.setBackground(greyBack);

        mainPanel.add(standartWerte);
    }

    private void titelSmall_mainPanel(){
        JLabel smallTitel = new JLabel();

        smallTitel.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
        smallTitel.setForeground(new Color(0, 0, 0));
        smallTitel.setBounds(60,10,250,25);
        //smallTitel.setText("Hier kommt noch text hin");
        
        mainPanel.add(smallTitel);
    }
    
    private void titel_mainPanel(){
        JPanel titel_Panel = new JPanel();
        JLabel titel_Label = new JLabel();
        JLabel underline_Label = new JLabel();

        titel_Panel.setLayout(null);
        titel_Panel.setBounds(0,50,550, 75);
        titel_Panel.setBackground(purpleBack);

        titel_Label.setFont(new Font("Comic Sans MS", Font.BOLD, 25));
        titel_Label.setForeground(greyForg);
        titel_Label.setBounds(60,15, titel_Panel.getWidth(),35);
        titel_Label.setText("AutoEntschuldigungen v1.1");

        underline_Label.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
        underline_Label.setForeground(greyForg);
        underline_Label.setBounds(62,45,titel_Panel.getWidth(),25);
        underline_Label.setText("by 32mika");

        titel_Panel.add(underline_Label);
        titel_Panel.add(titel_Label);
        mainPanel.add(titel_Panel);
    }

    private void txtB_name(){
        txtB_name_label();
        txtB_name_box();
    }

    private void txtB_name_label(){
        JLabel boxname_label = new JLabel();

        boxname_label.setFont(smallFont);
        boxname_label.setForeground(new Color(0, 0, 0));
        boxname_label.setBounds(60,140,500,35);
        boxname_label.setText("Name*:");

        mainPanel.add(boxname_label);
    }

    private void txtB_name_box(){
        box_name = new JTextField("Vorname Nachname",  20);

        box_name.setBounds(234, 140, 153, 35);
        box_name.setForeground(Color.gray);


        box_name.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if(box_name.getText().equals("Vorname Nachname")){
                    System.out.println("Focus bekommen");
                    box_name.setText("");
                    box_name.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(box_name.getText().equals("Vorname Nachname") | box_name.getText().equals("")){
                    System.out.println("verloren fokus");
                    box_name.setText("Vorname Nachname");
                    box_name.setForeground(Color.gray);
                }
            }

        });

        mainPanel.add(box_name);
    }

    public void setBoxText(String s){
        box_name.setText(s);
        box_name.setForeground(Color.BLACK);
    }


    private void datumAuswahl(){
        datum = new JToggleButton();
        zeitraum = new JToggleButton();
        ButtonGroup dateSelect = new ButtonGroup();
        ActionListener toggle_datum;

        datum = buttonBuild("einzel Datum", 60, 200);
        zeitraum = buttonBuild("Zeitraum", 260, 200);



        dateSelect.add(datum);
        dateSelect.add(zeitraum);
        datum.setSelected(true);

        JToggleButton finalDatum = datum;
        JToggleButton finalZeitraum = zeitraum;

        toggle_datum = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(finalDatum.isSelected()){
                    endDatumAuswahl.setEnabled(false);
                }

                if (finalZeitraum.isSelected()){
                    endDatumAuswahl.setEnabled(true);
                }
            }
        };

        datum.addActionListener(toggle_datum);
        zeitraum.addActionListener(toggle_datum);

        mainPanel.add(zeitraum);
        mainPanel.add(datum);
    }

    private void eingabeDatum(){
        JPanel dateChooserPanel = new JPanel();
        JLabel startdatum_label = new JLabel();
        JLabel enddatum_label = new JLabel();

        startDatumAuswahl = new JDateChooser();
        endDatumAuswahl = new JDateChooser();

        dateChooserPanel.setBounds(60,270,350, 75);
        dateChooserPanel.setBackground(greyBack);

        startdatum_label.setFont(smallFont);
        enddatum_label.setFont(smallFont);
        startdatum_label.setText("Startdatum*:");
        enddatum_label.setText("Enddatum:");


        endDatumAuswahl.setEnabled(false);

        dateChooserPanel.setLayout(new GridLayout(2, 2));
        dateChooserPanel.add(startdatum_label);
        dateChooserPanel.add(startDatumAuswahl);
        dateChooserPanel.add(enddatum_label);
        dateChooserPanel.add(endDatumAuswahl);

        mainPanel.add(dateChooserPanel);
    }


    private JToggleButton buttonBuild(String txt, int x, int y){
        JToggleButton button = new JToggleButton();
        
        button.setBounds(x, y, 170, 35);
        
        button.setForeground(greyForg);
        button.setFont(smallFont);
        button.setText(txt);

        button.setBackground(purpleBack);
        button.setFocusPainted(false);
        button.setOpaque(true);
        
        return button;
    }

    private void txtB_speichern(){
        JLabel speichernLabel = new JLabel();
        speichernField = new JTextField("Speicherpfard",  20);

        speichernLabel.setFont(smallFont);
        speichernLabel.setForeground(new Color(0, 0, 0));
        speichernLabel.setBounds(60,370,500,35);
        speichernLabel.setText("Speicherpfard*:");

        

        speichernField.setBounds(234, 370, 153, 35);
        speichernField.setForeground(Color.gray);

        speichernField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if(speichernField.getText().equals("Speicherpfard")){
                    System.out.println("Focus bekommen");
                    speichernField.setText("");
                    speichernField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(speichernField.getText().equals("Speicherpfard") | speichernField.getText().equals("")){
                    System.out.println("verloren fokus");
                    speichernField.setText("Speicherpfard");
                    speichernField.setForeground(Color.gray);
                }
            }

        });

        mainPanel.add(speichernField);
        mainPanel.add(speichernLabel);
    }

    public void setSpeichernFiel(String s){
        speichernField.setText(s);
        speichernField.setForeground(Color.BLACK);
    }
    
    private void addresseBox(){
        ortLabelBox();
        straßeLabelBox();
        
        
    }
    
    private void ortLabelBox(){
        ortLabel = new JLabel();
        ortField = new JTextField();
        int y = 420;
        

        ortLabel.setBounds(60, y, 185, 35);
        ortLabel.setText("PLZ und Ort*:");
        ortLabel.setForeground(Color.BLACK);
        ortLabel.setFont(smallFont);

        ortField = new JTextField("Postleitzahl Ort",  20);
        ortField.setForeground(Color.GRAY);
        ortField.setBounds(234, y, 153, 35);

        JTextField finalOrtField = ortField;

        ortField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent d) {
                if(finalOrtField.getText().equals("Postleitzahl Ort")){
                    System.out.println("Focus bekommen1");
                    finalOrtField.setText("");
                    finalOrtField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent d) {
                if(finalOrtField.getText().equals("Postleitzahl Ort") | finalOrtField.getText().equals("")){
                    System.out.println("verloren fokus2");
                    finalOrtField.setText("Postleitzahl Ort");
                    finalOrtField.setForeground(Color.gray);
                }
            }

        });

        mainPanel.add(ortLabel);
        mainPanel.add(ortField);
    }

    public void setOrtFieldText(String s){
        ortField.setText(s);
        ortField.setForeground(Color.BLACK);
    }
    
    private void straßeLabelBox(){
        straßeLabel = new JLabel();
        straßeField = new JTextField();
        int y = 470;


        straßeLabel.setBounds(60, y, 185, 35);
        straßeLabel.setText("Straße und HsNr*:");
        straßeLabel.setForeground(Color.BLACK);
        straßeLabel.setFont(smallFont);

        straßeField = new JTextField("Straße Hausnummer",  20);
        straßeField.setForeground(Color.GRAY);
        straßeField.setBounds(234, y, 153, 35);

        JTextField finalstraßeField = straßeField;

        straßeField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent d) {
                if(finalstraßeField.getText().equals("Straße Hausnummer")){
                    System.out.println("Focus bekommen1");
                    finalstraßeField.setText("");
                    finalstraßeField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent d) {
                if(finalstraßeField.getText().equals("Straße Hausnummer") | finalstraßeField.getText().equals("")){
                    System.out.println("verloren fokus2");
                    finalstraßeField.setText("Straße Hausnummer");
                    finalstraßeField.setForeground(Color.gray);
                }
            }

        });

        mainPanel.add(straßeLabel);
        mainPanel.add(straßeField);
    }

    public void setStraßeFieldText(String s){
        straßeField.setText(s);
        straßeField.setForeground(Color.BLACK);
    }
    
    private void titel(){
        JLabel titel = new JLabel();

        titel.setBounds(2, 30, 250, 100);
        titel.setText("AutoEntschuldigungen");
        titel.setForeground(Color.white);
        titel.setFont(bigFont);
        titel.setHorizontalAlignment(JTextField.CENTER);
        titel.setVerticalAlignment(JTextField.TOP);

        menu_Panel.add(titel);
    }

    private void picto_title(){
        JLabel picto_text = new JLabel();
        ImageIcon report = new ImageIcon("/Users/mikadobrowolski/autoEntschuldigungen/autoEntschuldigung/src/main/resources/Pictures/report_white.png");

        Image image = report.getImage();
        Image scaledImage = image.getScaledInstance(25, 25, Image.SCALE_SMOOTH);
        report = new ImageIcon(scaledImage);

        picto_text.setBounds(65, 130, 132, 25);

        picto_text.setFont(smallFont);
        picto_text.setForeground(Color.white);
        picto_text.setText("autoEnt");

        picto_text.setIcon(report);
        picto_text.setIconTextGap(20);

        picto_text.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                picto_text.setForeground(Color.red);
            }

            public void mouseExited(MouseEvent e) {
                picto_text.setForeground(Color.white);
            }
        });

        menu_Panel.add(picto_text);
    }

    private void filler(){
        ImageIcon clock = new ImageIcon("/Users/mikadobrowolski/autoEntschuldigungen/autoEntschuldigung/src/main/resources/Pictures/Uhr-weiß.png");

        Image image = clock.getImage();
        Image scaledImage = image.getScaledInstance(25, 25, Image.SCALE_SMOOTH);
        clock = new ImageIcon(scaledImage);


        for(int i = 0; i < 3; i++){
            JLabel filler = new JLabel();

            filler.setBounds(65, 185 + i * 50, 132, 25);

            filler.setFont(smallFont);
            filler.setForeground(Color.white);
            filler.setText("Soon");

            filler.setIcon(clock);
            filler.setIconTextGap(20);
            filler.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    filler.setForeground(Color.red);
                }

                public void mouseExited(MouseEvent e) {
                    filler.setForeground(Color.white);
                }
            });


            filler.setVisible(true);
            menu_Panel.add(filler);
        }
        
    }
}
