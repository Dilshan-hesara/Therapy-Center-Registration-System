package lk.cw.bo;

import lk.cw.bo.custom.impl.PatientBOImpl;
import lk.cw.bo.custom.impl.TherapistBOImpl;
import lk.cw.bo.custom.impl.UserBOImpl;

public class BOFactory {
    private static BOFactory boFactory;
    private BOFactory(){
    }
    public static BOFactory getBoFactory(){
        return (boFactory==null)? boFactory=new BOFactory() : boFactory;
    }

    public enum BOTypes{
        PATIENT, PAYMENT,THERAPIST,THERAPYOROGRAM,THERAPYSESSION,USER
    }

    public SuperBO getBO(BOTypes types){
        switch (types){

            case USER:
                return  new UserBOImpl();

            case PATIENT:
                return  new PatientBOImpl();


            case THERAPIST :
                return  new TherapistBOImpl();


            default:
                return null;
        }
    }

}
