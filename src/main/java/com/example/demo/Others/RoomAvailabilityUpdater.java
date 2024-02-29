package com.example.demo.Others;

import com.example.demo.entity.Booking;
import com.example.demo.entity.Room;
import com.example.demo.repo.BookingRepo;
import com.example.demo.repo.RoomRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class RoomAvailabilityUpdater {
    @Autowired
    private BookingRepo bookingRepo;
    @Autowired
    private RoomRepo roomRepo;

    @Scheduled(fixedDelay = 86400000)
    public void updateRoomAvailability(){
        System.out.println("hello");
        Date currentDate =new Date();
        handlePayAtHotelBookings(currentDate);


        // Handle bookings with "Online Payment" status
//        handleOnlinePaymentBookings(currentDate);
    }

    private void handlePayAtHotelBookings(Date currentDate) {
        List<Booking> payAtHotelBookings=bookingRepo.findByCheckOutDateBeforeAndPaymentStatus(currentDate, "Pay At Hotel");
        for(Booking booking:payAtHotelBookings){
            int stayDuration=calculateStayDuration(booking.getCheckInDate(), booking.getCheckOutDate());
            Date checkOutDate=calculateCheckOutDate(booking.getCheckInDate(), stayDuration);
            if(currentDate.after(checkOutDate)){
                Room room =booking.getRoom();
                room.setAvailability(true);
                roomRepo.save(room);
            }
        }
    }

    private Date calculateCheckOutDate(Date checkInDate, int stayDuration) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(checkInDate);
        calendar.add(Calendar.DAY_OF_MONTH, stayDuration); // Add stay duration to check-in date
        return calendar.getTime();
    }

    private int calculateStayDuration(Date checkInDate, Date checkOutDate) {

        long difference = checkOutDate.getTime() - checkInDate.getTime();
        return (int) TimeUnit.DAYS.convert(difference, TimeUnit.MILLISECONDS);
    }

//    private void handleOnlinePaymentBookings(Date currentDate) {
//        List<Booking> onlinePaymentBookings = bookingRepo.findByCheckOutDateBeforeAndPaymentStatus(currentDate, "Online Payment");
//        for (Booking booking : onlinePaymentBookings) {
//            // Handle online payment logic (if needed)
//            // For example, send payment reminders, update payment status, etc.
//        }
//    }

}
