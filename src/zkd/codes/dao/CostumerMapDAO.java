package zkd.codes.dao;

import zkd.codes.domain.Costumer;

import java.util.HashMap;
import java.util.Map;

public class CostumerMapDAO implements ICostumerDAO {

    private Map<Long, Costumer> data;

    public CostumerMapDAO(){
        this.data = new HashMap<>();
    }

    @Override
    public boolean create(Costumer object) {
        if(this.data.containsKey(object.getCpf())){
            return false;
        }
        this.data.put(object.getCpf(), object);
        return true;
    }

    @Override
    public Costumer search(Long cpf) {
        Costumer object = this.data.get(cpf);
        return object;
    }

    @Override
    public void update(Costumer newObjectData) {
        Costumer oldObjectData = this.data.get(newObjectData.getCpf());
        oldObjectData.setName(newObjectData.getName());
        oldObjectData.setPhoneNumber(newObjectData.getPhoneNumber());
        oldObjectData.setAddress(newObjectData.getAddress());
        oldObjectData.setCity(newObjectData.getCity());
        oldObjectData.setState(newObjectData.getState());
    }

    @Override
    public void delete(Long cpf) {
        Costumer object = this.data.get(cpf);
        this.data.remove(cpf, object);
    }
}
