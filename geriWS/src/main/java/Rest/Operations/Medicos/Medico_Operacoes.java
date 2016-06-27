/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Rest.Operations.Medicos;

import Models.Medico;
import Utils.Database;
import Utils.Status;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author luis
 */
@Path("medicos")
public class Medico_Operacoes {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() throws ParseException {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateInString = "1991-11-02";
        //Date todayWithZeroTime = formatter.parse(formatter.format(today));
        Date date = dateFormat.parse(dateInString);

        Medico newMedi = new Medico(14006392, "luis","luis", 1, date);
        Gson gson = new Gson();

        return gson.toJson(newMedi);
    }

    @Path("/insert")
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public String newMedico(String content) {
        int insert = 0;
        boolean canInsert = false;
        SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd");
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        Medico newMed;
        try {
            newMed = gson.fromJson(content, Medico.class);
        } catch(JsonSyntaxException ex)  {
            Response.ResponseBuilder builder = Response.status(Response.Status.NOT_ACCEPTABLE);
                builder.header("Access-Control-Allow-Origin", "*");
                Response response = builder.build();
                throw new WebApplicationException(response);
        }
        Database db = new Database();

        String checkCC = "SELECT * FROM MEDI WHERE CC = ?";
        String checkTipoEspe = "SELECT * FROM TIPO_ESPE";
        ResultSet rsCC = db.executeQuery(checkCC, "" + newMed.getCC());
        ResultSet rsTipoEspe = db.executeQuery(checkTipoEspe);
        try {
            if (rsCC.next()) {
                db.close();
                Response.ResponseBuilder builder = Response.status(Response.Status.CONFLICT);
                builder.header("Access-Control-Allow-Origin", "*");
                Response response = builder.build();
                throw new WebApplicationException(response);
            } else {
                while (rsTipoEspe.next()) {
                    if (newMed.getTipo_espe() == rsTipoEspe.getInt("ID_ESPE")) {
                        canInsert = true;
                    }
                }
                if (canInsert && newMed.getPass().length()>=6)  {
                    String date = sdfTime.format(newMed.getData_nasc());
                    String putMed = "INSERT INTO MEDI (CC,NOME,PASS,TIPO_ESPE,DATA_NASC) VALUES(?,?,?,?,?)";
                    insert = db.executeUpdate(putMed, "" + newMed.getCC(), newMed.getNome(),newMed.getPass(), "" + newMed.getTipo_espe(), date);
                } else {
                    db.close();
                    Response.ResponseBuilder builder = Response.status(Response.Status.NOT_ACCEPTABLE);
                    builder.header("Access-Control-Allow-Origin", "*");
                    Response response = builder.build();
                    throw new WebApplicationException(response);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Medico_Operacoes.class.getName()).log(Level.SEVERE, null, ex);
        }
        db.close();
        return gson.toJson(new Status(200));
    }

}
