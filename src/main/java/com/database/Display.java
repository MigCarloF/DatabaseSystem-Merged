package com.database;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Display extends JFrame{
	Database database = Database.database;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static JTextArea txt;
	JButton button = new JButton("Add");
	JButton print = new JButton("Print");
	JButton busAdd = new JButton("Bus");
	JButton minibusAdd = new JButton("Mini Bus");
	JButton printBus = new JButton("Bus Print");
	ArrayList<Fee> listoffees = new ArrayList<>();

	int ctr = 1;
	Display() throws IOException {
		//FirebaseDB.initFirebase();
		setLayout(new FlowLayout());
		txt = new JTextArea("test");
		
		add(button);
		add(print);
		add(busAdd);
		add(minibusAdd);
		add(printBus);
		add(txt);

		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Fee fee = new Fee(true,true,"09:00","" + ctr,"Employee:" + ctr, LocalDate.of(2017, Month.AUGUST, 21),"Plate: shyet" + ctr);
				FirebaseDB.addFee(fee);


				ctr++;
			}
		});

		busAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String buses[] = {"CERES LINER", "SUNRAYS", "SOCORRO", "METROLINK"};
				String contactperson[] = {"GreenTeaExpert", "Double A", "Jimbro", "Natsci", "WAOW", "WaterBender"};
				Bus bus = new Bus("ABC" + ctr, buses[ThreadLocalRandom.current().nextInt(0, 3 + 1)],
						"" + ctr + 3, contactperson[ThreadLocalRandom.current().nextInt(0, 5 + 1)],
						"09333784505", "100 cubic meter", "CEB - DUBAI", "100", "1000");
				FirebaseDB.addBus(bus);
				ctr++;
			}
		});

		minibusAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String buses[] = {"CERES LINER", "JEGANS", "CALVO", "COROMINAS", "GABE TRANSIT", "CANONEO", "JHADE"};
				String contactperson[] = {"GreenTeaExpert", "Double A", "Jimbro", "Natsci", "WAOW", "WaterBender"};
				Bus bus = new Bus("ABC" + ctr, buses[ThreadLocalRandom.current().nextInt(0, 6 + 1)],
						contactperson[ThreadLocalRandom.current().nextInt(0, 5 + 1)], "09333784505",
						"90 cubic meter","CEB - DUBAI", "100", "1000");
				FirebaseDB.addBus(bus);
				ctr++;
			}
		});


		print.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				FirebaseDB.getAllFees(new DataListener() {
					@Override
					public void newDataReceived(ArrayList<Fee> feeslist) {
						listoffees = feeslist;
						//System.out.println(listoffees.size() + "ASdsad");
						//System.out.println("tae");
						setFeesList(feeslist);
						for(Fee f : feeslist){
							System.out.println(f.getArrivalFee() + "\t" + f.getBus_plate() + "\t"
									+ f.getDatePaid() + "\t" + f.getEmployeeID() + "\t" + f.getLoadingFee() +
									"\t" + f.getOrNum() + "\t" + f.getTimePaid());
						}
					}
				});

			}
		});

		printBus.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				FirebaseDB.getAllBus(new DotaListener() {
					@Override
					public void dataReceived(ArrayList<Bus> buslist) {
						for(Bus b : buslist){
							System.out.println(b.getBusNumber() + "\t" + b.getBusType() + "\t"
									+ b.getCompany() + "\t" + b.getPlateNo());
						}
					}
				});

			}
		});
		setSize(300,400);

		setVisible(true);

		addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                System.exit(0);
            }
        });
}

void setFeesList(ArrayList<Fee> feee){
		listoffees = new ArrayList<>();
}

	public static void main(String[] args) throws IOException {
		 //new Display();
		 //Database db = new Database();
		 FirebaseDB.initFirebase();
		 //FirebaseDB.initFee();
		 //Database.getBusDataByRFID(1);
		//FirebaseDB.startRTDataListener();
		 FirebaseDB.run();
		new Display();

	    }

}
