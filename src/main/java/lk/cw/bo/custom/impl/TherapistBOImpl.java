package lk.cw.bo.custom.impl;

import lk.cw.bo.custom.TherapistBO;
import lk.cw.dao.DAOFactory;
import lk.cw.dao.custom.TherapistDAO;
import lk.cw.dto.TherapistDTO;
import lk.cw.entity.Therapist;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TherapistBOImpl implements TherapistBO {


    TherapistDAO therapistDAO = (TherapistDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.THERAPIST);
    @Override
    public boolean save(TherapistDTO therapistDTO) throws IOException, SQLException {
        return therapistDAO.save(new Therapist(therapistDTO.getTherapistId(), therapistDTO.getTherapistName(), therapistDTO.getSpecialization(), therapistDTO.getAvailability()));
    }

    @Override
    public String getNextId() throws SQLException, IOException {
        return therapistDAO.getNextId();
    }

    @Override
    public TherapistDTO findById(String therapistId) throws SQLException, ClassNotFoundException {
        Therapist therapist = therapistDAO.findById(therapistId);
        return new TherapistDTO(therapist.getTherapistId(),therapist.getTherapistName(),therapist.getSpecialization(),therapist.getTherapistId());
    }

    @Override
    public ArrayList<String> getAllTherapistIDs() throws SQLException, ClassNotFoundException, IOException {
        ArrayList<String> allIds = new ArrayList<>();
        ArrayList<String>all = therapistDAO.getAllTherapistIDs();
        for(String p: all){
            allIds.add(p);

        }
        return allIds;
    }

//     for (Inventroy inventroy : inventroyDtoArrayList) {
//        inventroyDtos.add(new InventroyDto(new Inventroy(
//                inventroy.getId(),
//                inventroy.getItemType(),
//                inventroy.getItemDescription(),
//                inventroy.getQty(),
//                inventroy.getProdId()
//        )));
//    }
//
//        return inventroyDtos;

    @Override
    public int getTotalTherapists() throws SQLException, ClassNotFoundException, IOException {
        return therapistDAO.getTotalTherapists();
    }

    @Override
    public List<TherapistDTO> getAll() throws SQLException, IOException {
        List<Therapist> therapists = therapistDAO.getAll();
        List<TherapistDTO> therapistDTOS = new ArrayList<>();

        for (Therapist therapist : therapists) {
            TherapistDTO therapistDTO = new TherapistDTO();
            therapistDTO.setTherapistId(therapist.getTherapistId());
            therapistDTO.setTherapistName(therapist.getTherapistName());
            therapistDTO.setSpecialization(therapist.getSpecialization());
            therapistDTO.setAvailability(therapist.getAvailability());
            therapistDTOS.add(therapistDTO);
        }
        return therapistDTOS;
    }

    @Override
    public boolean update(TherapistDTO therapistDTO) throws IOException, SQLException {
        return therapistDAO.update(new Therapist(therapistDTO.getTherapistId(), therapistDTO.getTherapistName(), therapistDTO.getSpecialization(), therapistDTO.getAvailability()));
    }

    @Override
    public boolean delete(String ID) throws SQLException, IOException {
        return therapistDAO.delete(ID);
    }

}
