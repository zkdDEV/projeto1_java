package zkd.codes.dao;

import zkd.codes.domain.Costumer;

import java.util.Collection;

public interface ICostumerDAO {
    // CRUD - Create, Read, Update and Delete
    public boolean create(Costumer object);

    public Costumer search(Long cpf);

    public void update(Costumer object);

    public void delete(Long cpf);
}
