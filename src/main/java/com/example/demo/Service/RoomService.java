package com.example.demo.Service;

import com.example.demo.dto.RoomDto;
import com.example.demo.entity.Hotel;
import com.example.demo.entity.Room;
import com.example.demo.repo.HotelRepo;
import com.example.demo.repo.RoomRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService {
    @Autowired
    RoomRepo roomRepo;
    @Autowired
    HotelRepo hotelRepo;
    public void addRoom(RoomDto roomDto) {
    try {
        Long hotelId= roomDto.getHotelId();
        Hotel hotel=hotelRepo.findByHotelId(hotelId);
        if(hotel==null){
            throw new IllegalArgumentException("Hotel not found with id: " + hotelId);
        }

        Room room=new Room();
        room.setRoomNumber(roomDto.getRoomNumber());
        room.setAvailability(true);
        room.setRoomType(roomDto.getRoomType());
        room.setPricePerNight(Double.parseDouble(roomDto.getPricePerNight()));
        room.setImages(roomDto.getImages());

        // Associate the retrieved Hotel entity with the new Room entity
        room.setHotel(hotel);

        // Save the new Room entity to the database
        roomRepo.save(room);
    } catch (Exception e) {
        throw new RuntimeException("Failed to add room", e);
    }
    }

    public List<Room> getRoomsByHotelIds(Long hotelId) {


        return roomRepo.findByHotel_HotelId(hotelId);

    }

    public List<Room> getRoomBySearch(String search) {
        return roomRepo.findByRoomNumberContainingIgnoreCase(search);

    }

    public List<Room> getAllRooms() {
        return roomRepo.findAll();
    }

    public Room getRoomById(Long roomId) {
        Optional<Room> roomOptional=roomRepo.findById(roomId);
        return roomOptional.orElse(null);



    }

    public Room updateRoom(Room existingRoom) {
        return roomRepo.save(existingRoom);
    }

    public boolean isRoomNumberUnique(String roomNumber, Long excludeRoomId) {
        Room room = roomRepo.findByRoomNumber(roomNumber);
        return room == null || (room.getRoomId() != null && room.getRoomId().equals(excludeRoomId));
    }
}
