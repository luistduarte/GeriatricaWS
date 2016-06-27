/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Rest.Operations.Utentes;

import Models.Utente;
import Models.UtentesList;
import Rest.Operations.Medicos.Medico_Operacoes;
import Utils.Database;
import Utils.Status;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author luis
 */
@Path("utentes")
public class Utente_Operacoes {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() throws ParseException {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateInString = "1991-11-02";
        //Date todayWithZeroTime = formatter.parse(formatter.format(today));
        Date date = dateFormat.parse(dateInString);

        Utente newMedi = new Utente(12345, "nome", date, 0, 0, 0, 0, 0, 0);
        Gson gson = new Gson();

        return gson.toJson(newMedi);
    }

    @Path("{medico}/insert")
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public String newUtent(@PathParam("medico") String CC_Medi, String content) {

        SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd");
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        Utente newUtente;

        try {
            newUtente = gson.fromJson(content, Utente.class);
        } catch (JsonSyntaxException ex) {
            Response.ResponseBuilder builder = Response.status(Response.Status.NOT_ACCEPTABLE);
            builder.header("Access-Control-Allow-Origin", "*");
            Response response = builder.build();
            throw new WebApplicationException(response);
        }
        Database db = new Database();

        String checkNumUten = "SELECT * FROM UTEN WHERE NUME_UTEN = ?";
        String checkCCMedi = "SELECT * FROM MEDI WHERE CC = ?";
        ResultSet rsNumeUtent = db.executeQuery(checkNumUten, "" + newUtente.getNume_uten());
        ResultSet rsCCMedi = db.executeQuery(checkCCMedi, CC_Medi);
        try {
            if (rsNumeUtent.next()) {
                db.close();
                Response.ResponseBuilder builder = Response.status(Response.Status.NOT_ACCEPTABLE);
                builder.header("Access-Control-Allow-Origin", "*");
                Response response = builder.build();
                throw new WebApplicationException(response);
            } else if (rsCCMedi.next()) {

                String date = sdfTime.format(newUtente.getData_nasc());
                String putUten = "INSERT INTO UTEN (NUME_UTEN,NOME_UTEN,DATA_NASC,CC_MEDI,AVAL_AVDB,AVAL_AIVD,AVAL_MARC,AVAL_AFEC,AVAL_COGN,AVAL_NUTR) VALUES(?,?,?,?,0,0,0,0,0,0)";
                db.executeUpdate(putUten, "" + newUtente.getNume_uten(), newUtente.getNome(), date, CC_Medi);

            } else {
                db.close();
                Response.ResponseBuilder builder = Response.status(Response.Status.NOT_ACCEPTABLE);
                builder.header("Access-Control-Allow-Origin", "*");
                Response response = builder.build();
                throw new WebApplicationException(response);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Medico_Operacoes.class.getName()).log(Level.SEVERE, null, ex);
        }
        db.close();
        return gson.toJson(new Status(200));

    }

    @Path("{medico}/list")
    @GET
    @Produces("application/json")
    public String getUtentList(@PathParam("medico") int CC_Medi) {

        List<Utente> UtentesList = new ArrayList<>();
        Database db = new Database();
        Gson gson = new Gson();
        String checkCCMedi = "SELECT * FROM UTEN WHERE CC_MEDI = ?";
        ResultSet rsCCMedi = db.executeQuery(checkCCMedi, "" + CC_Medi);
        Utente newUtent = null;
        try {
            if (rsCCMedi.next()) {
                newUtent = new Utente(rsCCMedi.getInt("NUME_UTEN"), rsCCMedi.getString("NOME_UTEN"));
                UtentesList.add(newUtent);
                while (rsCCMedi.next()) {
                    newUtent = new Utente(rsCCMedi.getInt("NUME_UTEN"), rsCCMedi.getString("NOME_UTEN"));
                    UtentesList.add(newUtent);
                }
            } else {
                db.close();
                Response.ResponseBuilder builder = Response.status(Response.Status.NOT_ACCEPTABLE);
                builder.header("Access-Control-Allow-Origin", "*");
                Response response = builder.build();
                throw new WebApplicationException(response);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Utente_Operacoes.class.getName()).log(Level.SEVERE, null, ex);
        }

        UtentesList toSend = new UtentesList(CC_Medi, UtentesList);
        db.close();
        return gson.toJson(toSend, UtentesList.class);

    }

    @Path("{medico}/{utente}")
    @GET
    @Produces("application/json")
    public String getUtentData(@PathParam("medico") int CC_Medi, @PathParam("utente") int utente) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        Database db = new Database();

        String checkCCMedi = "SELECT * FROM UTEN WHERE CC_MEDI = ? AND NUME_UTEN = ?";
        ResultSet rsCCMedi = db.executeQuery(checkCCMedi, "" + CC_Medi, "" + utente);
        Utente newUtent = null;
        try {
            if (rsCCMedi.next()) {
                newUtent = new Utente(rsCCMedi.getInt("NUME_UTEN"), rsCCMedi.getString("NOME_UTEN"),
                        rsCCMedi.getDate("DATA_NASC"), rsCCMedi.getInt("AVAL_AVDB"), rsCCMedi.getInt("AVAL_AIVD"),
                        rsCCMedi.getInt("AVAL_MARC"), rsCCMedi.getInt("AVAL_AFEC"), rsCCMedi.getInt("AVAL_COGN"),
                        rsCCMedi.getInt("AVAL_NUTR"));
            } else {
                db.close();
                Response.ResponseBuilder builder = Response.status(Response.Status.NOT_ACCEPTABLE);
                builder.header("Access-Control-Allow-Origin", "*");
                Response response = builder.build();
                throw new WebApplicationException(response);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Utente_Operacoes.class.getName()).log(Level.SEVERE, null, ex);
        }

        db.close();
        return gson.toJson(newUtent, Utente.class);

    }

    @Path("{medico}/{utente}/{tipo_aval}")
    @GET
    @Produces("application/json")
    public String getUtentAvalList(@PathParam("medico") int CC_Medi, @PathParam("utente") int utente, @PathParam("tipo_aval") int tipo_aval) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        Database db = new Database();

        String checkCCMedi = "SELECT * FROM AVAL WHERE CC_MEDI = ? AND NUME_UTEN = ? AND TIPO_AVAL = ?";
        ResultSet rsAval = db.executeQuery(checkCCMedi, "" + CC_Medi, "" + utente, "" + tipo_aval);
        Utente newUtent = null;
        try {
            if (rsAval.next()) {

                while (rsAval.next()) {

                }

            } else {
                db.close();
                Response.ResponseBuilder builder = Response.status(Response.Status.NOT_ACCEPTABLE);
                builder.header("Access-Control-Allow-Origin", "*");
                Response response = builder.build();
                throw new WebApplicationException(response);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Utente_Operacoes.class.getName()).log(Level.SEVERE, null, ex);
        }

        db.close();
        return gson.toJson(newUtent, Utente.class);
    }

}
