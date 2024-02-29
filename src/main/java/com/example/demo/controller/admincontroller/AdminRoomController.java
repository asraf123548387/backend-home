package com.example.demo.controller.admincontroller;

import com.example.demo.Service.HotelService;
import com.example.demo.Service.RoomService;
import com.example.demo.dto.RoomDto;
import com.example.demo.entity.Hotel;
import com.example.demo.entity.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminRoomController {
    @Autowired
    HotelService hotelService;
    @Autowired
    RoomService roomService;


    @GetMapping("/adminHotelListInAddRoom/{adminUserId}")
    public ResponseEntity<List<Hotel>> getHotelsForAdminForAddRoom(@PathVariable Long adminUserId){
        List<Hotel> hotels=hotelService.getHotelsForAdmin(adminUserId);
        return  ResponseEntity.ok(hotels);
    }

    @PostMapping("/adminAddRoom")
    public ResponseEntity<String> addRoom(@RequestBody RoomDto roomDto){
        try{


            roomService.addRoom(roomDto);
            return new ResponseEntity<>("Room added successfully", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Failed to addRoom",HttpStatus.INTERNAL_SERVER_ERROR);


        }
    }

@GetMapping("/roomList")
    public ResponseEntity<?> getAllHotels(@RequestParam(name = "search",required = false)String search){
        try{
            List<Room> rooms;
            if(search !=null && !search.isEmpty()){
                rooms=roomService.getRoomBySearch(search);
            }else{
                rooms=roomService.getAllRooms();
            }
            return ResponseEntity.ok(rooms);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("error fetching users");
        }
}
    @GetMapping("/room/{roomId}")
    public ResponseEntity<Room> getRoomByIdDetails(@PathVariable Long roomId){
        Room room=roomService.getRoomById(roomId);
        return ResponseEntity.ok(room);
    }
    @PutMapping("/room/{roomId}")
    public ResponseEntity<Room> updateRoom(@PathVariable Long roomId,@RequestBody RoomDto roomDto){
        try{
            Room existingRoom=roomService.getRoomById(roomId);
            if(existingRoom==null){
                return ResponseEntity.notFound().build();
            }
            if (!roomService.isRoomNumberUnique(roomDto.getRoomNumber(), roomId)) {
                return ResponseEntity.badRequest().body(null);
            }
            existingRoom.setRoomNumber(roomDto.getRoomNumber());
          existingRoom.setRoomType(roomDto.getRoomType());
          existingRoom.setImages(roomDto.getImages());
          existingRoom.setPricePerNight(Double.parseDouble(roomDto.getPricePerNight()));
        Room updatedRoom=roomService.updateRoom(existingRoom);
            return ResponseEntity.ok(updatedRoom);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(null);
        }
    }







}
