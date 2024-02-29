package com.example.demo.controller.userController;

import com.example.demo.Service.HotelService;
import com.example.demo.Service.RoomService;
import com.example.demo.dto.RoomDetailsDto;
import com.example.demo.entity.Hotel;
import com.example.demo.entity.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/userCheckOut")
public class UserCheckOutController {


    @Autowired
    RoomService roomService;
    @Autowired
    HotelService hotelService;


    @GetMapping("/getRoom/{roomId}")
    public ResponseEntity<RoomDetailsDto> getRoomByIdForCheckOut(@PathVariable Long roomId){
        Room room =roomService.getRoomById(roomId);

        RoomDetailsDto roomDetailsDTO = new RoomDetailsDto();
        roomDetailsDTO.setRoomId(room.getRoomId());
        roomDetailsDTO.setRoomNumber(room.getRoomNumber());
        roomDetailsDTO.setPricePerNight(String.valueOf(room.getPricePerNight()));

        roomDetailsDTO.setImages(room.getImages());
        roomDetailsDTO.setRoomType(room.getRoomType());
        roomDetailsDTO.setHotelId(room.getHotel().getHotelId()); // Assuming 'Hotel' entity has a 'hotelId' field

        return ResponseEntity.ok().body(roomDetailsDTO);
    }




    @GetMapping("/getHotel/{hotelId}")
    public ResponseEntity<Hotel> getHotelById(@PathVariable Long hotelId) {
        Hotel hotel = hotelService.getHotelById(hotelId);

        return ResponseEntity.ok().body(hotel);
    }
}
