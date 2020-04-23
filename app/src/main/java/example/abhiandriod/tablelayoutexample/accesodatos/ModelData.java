package example.abhiandriod.tablelayoutexample.accesodatos;

import java.util.ArrayList;
import java.util.List;

import example.abhiandriod.tablelayoutexample.logicadenegocio.JobApplication;
import example.abhiandriod.tablelayoutexample.logicadenegocio.Usuario;

public class ModelData {
    private List<Usuario> usuarios;
    private  List<JobApplication> listajobApplications;

    private static ModelData modelData;

    private ModelData() {
        usuarios = InitUsuarios();//Se cargan los usuarios
        listajobApplications = initApplicatines(); //Se cargan los formularios
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


    public List<JobApplication> initApplicatines(){
        listajobApplications=new ArrayList<>();
        JobApplication jobApplication = new JobApplication("Diego", "Salazar", "desampa", "desampa por el chino", "Alajuela", "Desamparados", "20304", "Costa Rica", "diego@gmail.com", "506", "123456789","System Engineer","23-04-2020" );
        listajobApplications.add(jobApplication);

        jobApplication = new JobApplication("Cristofer", "Madriz", "calle Rojas", "por la escuela", "Alajuela", "Grecia", "20336","España","cmadriz@gmail.com","800", "45223390", "Electronic Engineer", "14-04-2020");
        listajobApplications.add(jobApplication);

        jobApplication = new JobApplication("Allison", "Valverde", "LA", "por la casa de Ricky Martin", "Los Angeles", "California", "11111","USA","amv@gmail.com","000", "2498765543", "Data Analist", "20-03-2020");
        listajobApplications.add(jobApplication);

        jobApplication = new JobApplication("Ilainne", "Rodriguez", "Moscú", "al frente de la Plaza Roja", "Moscú", "Moscova", "101001–135999","Rusia","ila@hotmail.com","7543", "888456744", "Data Miner", "05-03-2020");
        listajobApplications.add(jobApplication);

        return listajobApplications;
    }



    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
}
