package lk.cw.bo;

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
    //Object creation logic for BO objects
    public SuperBO getBO(BOTypes types){
        switch (types){

            case USER:
                return (SuperBO) new UserBOImpl();




            default:
                return null;
        }
    }

}
