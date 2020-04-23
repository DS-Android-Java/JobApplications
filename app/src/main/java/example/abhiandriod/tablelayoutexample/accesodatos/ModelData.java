package example.abhiandriod.tablelayoutexample.accesodatos;

import java.util.ArrayList;
import java.util.List;

import example.abhiandriod.tablelayoutexample.logicadenegocio.Usuario;

public class ModelData {
    private List<Usuario> usuarios;

    private static ModelData modelData;

    private ModelData() {
        usuarios = InitUsuarios();//Se cargan los usuarios
    }

    public static ModelData getInstance(){
        if(modelData == null){
            modelData = new ModelData();
        }
        return modelData;
    }

    public List<Usuario> InitUsuarios(){
        List<Usuario> users = new ArrayList<>();
        users.add(new Usuario("@diego", "diego", "administrador"));
        users.add(new Usuario("@allison", "allimv", "administrador"));
        users.add(new Usuario("@vane", "vanessa", "estandar"));
        return users;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
}
