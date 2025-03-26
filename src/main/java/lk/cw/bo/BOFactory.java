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
       USER, PATIENT,THERAPIST,THERAPYOROGRAM,THERAPYSESSION,PATIENT_REG,PAYMENT
    }

    public SuperBO getBO(BOTypes types){
        switch (types){

            case USER:
                return  new UserBOImpl();

            case PATIENT:
                return  new PatientBOImpl();


            case THERAPIST :
                return  new TherapistBOImpl();


                case THERAPYOROGRAM :
                    return new TherapistBOImpl();
            default:
                return null;
        }
    }

}
