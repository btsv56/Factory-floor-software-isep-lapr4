package eapli.base.app.smm.console.JavaUDPCommunication.utils;

public enum MessageType{
    HELLO(0),
    MSG(1),
    CONFIG(2),
    RESET(3),
    ACK(150),
    NACK(151);

    private Integer msgCode;

    MessageType(Integer msgCode){
        this.msgCode = msgCode;
    }

    public Integer getMsgCode(){
        return this.msgCode;
    }
}