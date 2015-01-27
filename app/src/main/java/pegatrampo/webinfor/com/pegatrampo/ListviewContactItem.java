package pegatrampo.webinfor.com.pegatrampo;

import java.io.Serializable;

/**
 * Created by 98287028191 on 16/12/14.
 */
public class ListviewContactItem implements Serializable {

    Integer Id;
    String name, phone;
    private static final long serialVersionUID = 1L;

    public ListviewContactItem(Integer id, String nome, String telefone) {
        this.Id = id;
        this.name = nome;
        this.phone = telefone;
    }

    public ListviewContactItem(){

    }

    public void setId(Integer id){ this.Id = id; }

    public Integer getId(){ return Id; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
