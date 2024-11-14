import java.util.ArrayList;

public class _Model {
    private HotelReservationSystem HRS;

    public Model() {
        this.HRS = new HotelReservationSystem();
    }
    //for interacting with HRS mismo

    //if create hotel success
    // containerForFeedbackLabel set visible true
    // orrrrr JOptionPane


    public Boolean createHotel(String newHotelName) {
        if (Hotel.checkIfValidName(true, newHotelName)){
            HRS.CreateHotel(true, newHotelName);
            return true;
        } else 
            return false;
        
    }

    public HotelReservationSystem getHRS() {
        return this.HRS;
    }

    public ArrayList<Room> getRoomsFromHotelFromHRS(int HotelIndex) {
        return this.HRS.getHotelsInHRS().get(HotelIndex).getHotelRooms();
    }

    public Hotel findHotelIndexFromUserSelection(int userSelectedHotel) {
        return this.HRS.getHotelsInHRS().get(userSelectedHotel);
    }

    public String findRoomType(Room room) {
        if (room instanceof StandardRoom)
            return "Standard Room";
        else if (room instanceof DeluxeRoom)
            return "Deluxe Room";
        else if (room instanceof ExecutiveRoom)
            return "Executive Room";
        else
            return null;
    }

    public Boolean checkIFDateValid (String date1, String date2) {
        Date CheckInDate = new Date ();
		Date CheckOutDate = new Date ();

		String[] dateParts = date1.split("-");
		int nDay = Integer.parseInt(dateParts[0]);
		CheckInDate.setnDay(nDay);
		int nMonth = Integer.parseInt(dateParts[1]);
		CheckInDate.setnMonth(nMonth);
		int nYear = Integer.parseInt(dateParts[2]);
		CheckInDate.setnYear(nYear);
		dateParts = date2.split("-");
		nDay = Integer.parseInt(dateParts[0]);
		CheckOutDate.setnDay(nDay);
		nMonth = Integer.parseInt(dateParts[1]);
		CheckOutDate.setnMonth(nMonth);
		nYear = Integer.parseInt(dateParts[2]);
		CheckOutDate.setnYear(nYear);

        if (CheckInDate.getnDay() == 31 || CheckOutDate.getnDay() == 1) {
			System.out.println("Apologies. Please re-check your Check-In and Check-Out dates as they violate some constraints.\n");
            return false;
		} else if (CheckInDate.isBefore(CheckOutDate) == false) {
			System.out.println("Please re-input your Check-In and Check-Out dates.");
            return false;
		} else {
			return true;
		}
    }

    /**
	 * This method is a getter for the number of days of the guest's stay.
	 * This method computes for such by first converting the date to the day of the year and finding the difference.
	 * @param date1 - this must be the check-in date of the reservation
	 * @param date2 - this must be the check-out date of the reservation
	 * @return nNumDaysOfStay - which refers to the number of days of stay of the guest's reservation
	 */
	public int getnNumDaysOfStay(Date date1, Date date2) {
        int nNumDaysOfStay = 0;
		if (date1.getnMonth() == date2.getnMonth())
			nNumDaysOfStay = (date2.getnDay() - date1.getnDay());
		else if (date1.getnMonth() < date2.getnMonth()) {
			nNumDaysOfStay = 0;
			for (int i = date1.dayInYear(date1); i < date2.dayInYear(date2) - 1; i++) {
				nNumDaysOfStay++;
			}
		}
		return nNumDaysOfStay;
	}

    /**
	 * This method checks if the room is booked on a specific date with an int dataype parameter.
	 * @param date - the date to check as an integer from the nth day of the year method converter.
	 * @return true if the room is booked on the date, false otherwise.
	 */
	public boolean checkIfRoomIsBooked(int date, boolean[] dateRoomReserved) {
		if (dateRoomReserved[date] == true)
			return true;
		else
			return false;
	}

    public double howMuchDiscount(double basePrice, String discountCode, Date CheckInDate, Date CheckOutDate) {
        boolean[] dateRoomReserved = new boolean[366];
        for (int i = 0; i < 365; i++) 
			dateRoomReserved[i] = false;
        for (int i = CheckInDate.dayInYear(CheckInDate); i < CheckOutDate.dayInYear(CheckOutDate); i++)
			dateRoomReserved[i] = true;

        if (discountCode.equals("I_WORK_HERE")){
			return (1 - 0.10);      // 10% discount --> 0.9x multiplier
		} else if (discountCode.equals("STAY4_GET1")){
			if (getnNumDaysOfStay(CheckInDate, CheckOutDate) >= 5) {
				return basePrice;   // 
			} else {
				return 1.0;           // 1.0x multiplier
			}
		} else if (discountCode.equals("PAYDAY")){
			if (CheckInDate.getnDay() == 15 || 
                CheckInDate.getnDay() == 30 ||
				checkIfRoomIsBooked( 15, dateRoomReserved) || 		//jan 15		sir sorry for this lazy implementation 
				checkIfRoomIsBooked( 30, dateRoomReserved) || 		//jan 30			HAHAHAH
				checkIfRoomIsBooked( 46, dateRoomReserved) || 		//feb 15			im so burntout
				checkIfRoomIsBooked( 75, dateRoomReserved) || 		//mar 15
				checkIfRoomIsBooked( 90, dateRoomReserved) || 
				checkIfRoomIsBooked(106, dateRoomReserved) || 
				checkIfRoomIsBooked(121, dateRoomReserved) || 
				checkIfRoomIsBooked(136, dateRoomReserved) || 
				checkIfRoomIsBooked(151, dateRoomReserved) || 
				checkIfRoomIsBooked(167, dateRoomReserved) || 
				checkIfRoomIsBooked(182, dateRoomReserved) || 
				checkIfRoomIsBooked(197, dateRoomReserved) || 
				checkIfRoomIsBooked(212, dateRoomReserved) || 
				checkIfRoomIsBooked(228, dateRoomReserved) || 
				checkIfRoomIsBooked(243, dateRoomReserved) || 
				checkIfRoomIsBooked(259, dateRoomReserved) || 
				checkIfRoomIsBooked(274, dateRoomReserved) || 
				checkIfRoomIsBooked(289, dateRoomReserved) || 
				checkIfRoomIsBooked(304, dateRoomReserved) || 
				checkIfRoomIsBooked(320, dateRoomReserved) || 
				checkIfRoomIsBooked(335, dateRoomReserved) || 		//nov 30
				checkIfRoomIsBooked(350, dateRoomReserved) || 		//dec 15
				checkIfRoomIsBooked(365, dateRoomReserved)) {		//dec 30
				if (CheckOutDate.getnDay() == 15 ||
					CheckOutDate.getnDay() == 30){
					return 1.0;
				} else {
					return 0.93;
					}
				} else {
					return 1.0;
				}
            } else {
                return 1.0;
            }
    }

    public double computeDTotalPriceOfBooking(double basePrice, double[] datePriceModifierMultiplier, double roomTypeMultiplier, String discountCode, String date1, String date2) {
        Date CheckInDate = new Date ();
		Date CheckOutDate = new Date ();

		String[] dateParts = date1.split("-");
		int nDay = Integer.parseInt(dateParts[0]);
		CheckInDate.setnDay(nDay);
		int nMonth = Integer.parseInt(dateParts[1]);
		CheckInDate.setnMonth(nMonth);
		int nYear = Integer.parseInt(dateParts[2]);
		CheckInDate.setnYear(nYear);
		dateParts = date2.split("-");
		nDay = Integer.parseInt(dateParts[0]);
		CheckOutDate.setnDay(nDay);
		nMonth = Integer.parseInt(dateParts[1]);
		CheckOutDate.setnMonth(nMonth);
		nYear = Integer.parseInt(dateParts[2]);
		CheckOutDate.setnYear(nYear);

        double dTotalPriceOfBooking = 0;

        double discount = howMuchDiscount(basePrice, discountCode, CheckInDate, CheckOutDate);

		for (int i = CheckInDate.dayInYear(CheckInDate); i < CheckOutDate.dayInYear(CheckOutDate); i++) {
			dTotalPriceOfBooking = dTotalPriceOfBooking + (basePrice * roomTypeMultiplier * datePriceModifierMultiplier[i]);
		}

        return dTotalPriceOfBooking * discount;
    }

    public Boolean SimulateBooking(String guestName, int hotelChoice, int roomChoice, double basePrice, double[] datePriceModifierMultiplier, double roomTypeMultiplier, String date1, String date2, String discountCode) {
        double totalCost = computeDTotalPriceOfBooking(basePrice, datePriceModifierMultiplier, roomTypeMultiplier, discountCode, date1, date2);
        if(HRS.SimulateBooking(true, guestName, hotelChoice, roomChoice, date1, date2, discountCode, totalCost))
            return true;
        else
            return false;
    }
}
