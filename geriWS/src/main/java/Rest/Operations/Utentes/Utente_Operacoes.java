package Rest.Operations.Utentes;

import Models.AvalList;
import Models.Avaliacao;
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
import java.sql.Timestamp;
import java.text.DateFormat;
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

        return "UTENTES OPERATIONS RUNNING";
    }

    /*
        http://localhost:8080/geriWS/webServices/utentes/12345/insert
        SEND:
        {
            "nume_uten": 12346,
            "nome": "nome",
            "data_nasc": "1990-12-01"
        }
        RECEIVE ON SUCESS:
        {
            "status": 200
        }
     */
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

    /*
        http://localhost:8080/geriWS/webServices/utentes/12345/list

        {
        "CC": 12345
        "UtentesList": [2]
            0:  {
                "nume_uten": 12345
                "nome": "nome1"
                "AVAL_AVDB": 0
                "AVAL_AIVD": 0
                "AVAL_MARC": 0
                "AVAL_AFEC": 0
                "AVAL_COGN": 0
                "AVAL_NUTR": 0
            }-
            1:  {
                "nume_uten": 123456
                "nome": "nome1"
                "AVAL_AVDB": 0
                "AVAL_AIVD": 0
                "AVAL_MARC": 0
                "AVAL_AFEC": 0
                "AVAL_COGN": 0
                "AVAL_NUTR": 0
            }   
        }
     */
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

    /*
        http://localhost:8080/geriWS/webServices/utentes/12345/12345

        RECEIVE:
        {
            "nume_uten": 12345
            "nome": "nome1"
            "data_nasc": "1991-11-02"
            "AVAL_AVDB": 1
            "AVAL_AIVD": 2
            "AVAL_MARC": 3
            "AVAL_AFEC": 4
            "AVAL_COGN": 5
            "AVAL_NUTR": 6
        }
     */
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

    /*
        http://localhost:8080/geriWS/webServices/utentes/12345/123456/2

        RECEIVE:
        {
        "num_utente": 123456
        "AvaliacaoList": [1]
            0:  {
                "descricao": "{myaval:"asdasd"} "
                "resumo": 2
                "time": "1991-11-05 12:00:00.000"
                "tipo_avaliacao": 2
                "nume_utente": 123456
                "cc_medi": 12345
            }
        }
     */
    @Path("{medico}/{utente}/{tipo_aval}")
    @GET
    @Produces("application/json")
    public String getUtentAvalList(@PathParam("medico") int CC_Medi, @PathParam("utente") int utente, @PathParam("tipo_aval") int tipo_aval) throws ParseException {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd hh:mm:ss.SSS").create();
        Database db = new Database();
        List<Avaliacao> AvaliacaoList = new ArrayList<>();
        Avaliacao aval = null;
        AvalList listaAval = null;
        String checkCCMedi = "SELECT * FROM AVAL WHERE CC_MEDI = ? AND NUME_UTEN = ? AND TIPO_AVAL = ?";
        ResultSet rsAval = db.executeQuery(checkCCMedi, "" + CC_Medi, "" + utente, "" + tipo_aval);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
        try {
            if (rsAval.next()) {
                Date parsedDate = dateFormat.parse(rsAval.getString("DATA_AVAL"));
                Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
                aval = new Avaliacao(rsAval.getString("DESC_AVAL"), rsAval.getInt("AVAL_RESU"), timestamp, rsAval.getInt("TIPO_AVAL"), utente, CC_Medi);
                AvaliacaoList.add(aval);

                while (rsAval.next()) {
                    parsedDate = dateFormat.parse(rsAval.getString("DATA_AVAL"));
                    timestamp = new java.sql.Timestamp(parsedDate.getTime());
                    aval = new Avaliacao(rsAval.getString("DESC_AVAL"), rsAval.getInt("AVAL_RESU"), timestamp, rsAval.getInt("TIPO_AVAL"), utente, CC_Medi);
                    AvaliacaoList.add(aval);
                }
                listaAval = new AvalList(utente, AvaliacaoList);
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
        return gson.toJson(listaAval, AvalList.class);
    }

    /*
        http://localhost:8080/geriWS/webServices/utentes/12345/12345/1/newAval
        SEND:
        {
            descricao : "dsada",
            resumo : 2
        }
        ON SUCCESS:
        {
            "status": 200
        }
     */
    @Path("{medico}/{utente}/{tipo_aval}/newAval")
    @POST
    @Produces("application/json")
    public String newAval(@PathParam("medico") int CC_Medi, @PathParam("utente") int utente, @PathParam("tipo_aval") int tipo_aval, String content) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd hh:mm:ss.SSS").create();
        Database db = new Database();
        Avaliacao aval = null;
        try {
            aval = gson.fromJson(content, Avaliacao.class);
        } catch (JsonSyntaxException ex) {
            Response.ResponseBuilder builder = Response.status(Response.Status.NOT_ACCEPTABLE);
            builder.header("Access-Control-Allow-Origin", "*");
            Response response = builder.build();
            throw new WebApplicationException(response);
        }

        String checkMediUten = "SELECT * FROM UTEN WHERE CC_MEDI = ? AND NUME_UTEN = ?";
        ResultSet rsCheckMediUten = db.executeQuery(checkMediUten, "" + CC_Medi, "" + utente);

        try {
            if (rsCheckMediUten.next()) {

                //utente pertence a medico
                String checkTipoAval = "SELECT * FROM TIPO_AVAL WHERE ID_AVAL = ?";
                ResultSet rsCheckTipoAval = db.executeQuery(checkTipoAval, "" + tipo_aval);
                if (rsCheckTipoAval.next()) {
                    //Tudo valido para poder inserir avaliacao

                    Date date = new Date();
                    long time = date.getTime();
                    Timestamp now = new Timestamp(time);

                    String newAval = "INSERT INTO AVAL (NUME_UTEN, CC_MEDI, DESC_AVAL, AVAL_RESU, DATA_AVAL, TIPO_AVAL) VALUES (?,?,?,?,?,?)";
                    db.executeUpdate(newAval, "" + utente, "" + CC_Medi, aval.getDescricao(), "" + aval.getResumo(),
                            now.toString(), "" + tipo_aval);

                    //update resumo on UTENT Table
                    String updateResumo;                
                    switch (tipo_aval) {
                        case 1:
                            updateResumo = "UPDATE UTEN SET AVAL_AVDB = ? WHERE NUME_UTEN = ?;";
                            db.executeUpdate(updateResumo, "" + aval.getResumo(), "" + utente);
                            break;
                        case 2:
                            updateResumo = "UPDATE UTEN SET AVAL_AIVD = ? WHERE NUME_UTEN = ?;";
                            db.executeUpdate(updateResumo, "" + aval.getResumo(), "" + utente);
                            break;
                        case 3:
                            updateResumo = "UPDATE UTEN SET AVAL_MARC = ? WHERE NUME_UTEN = ?;";
                            db.executeUpdate(updateResumo, "" + aval.getResumo(), "" + utente);
                            break;
                        case 4:
                            updateResumo = "UPDATE UTEN SET AVAL_AFEC = ? WHERE NUME_UTEN = ?;";
                            db.executeUpdate(updateResumo, "" + aval.getResumo(), "" + utente);
                            break;
                        case 5:
                            updateResumo = "UPDATE UTEN SET AVAL_COGN = ? WHERE NUME_UTEN = ?;";
                            db.executeUpdate(updateResumo, "" + aval.getResumo(), "" + utente);
                            break;
                        case 6:
                            updateResumo = "UPDATE UTEN SET AVAL_NUTR = ? WHERE NUME_UTEN = ?;";
                            db.executeUpdate(updateResumo, "" + aval.getResumo(), "" + utente);
                            break;
                        default:
                            break;

                    }
                } else {
                    db.close();
                    Response.ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
                    builder.header("Access-Control-Allow-Origin", "*");
                    Response response = builder.build();
                    throw new WebApplicationException(response);
                }

            } else {
                db.close();
                Response.ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
                builder.header("Access-Control-Allow-Origin", "*");
                Response response = builder.build();
                throw new WebApplicationException(response);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Utente_Operacoes.class.getName()).log(Level.SEVERE, null, ex);
        }

        db.close();
        return gson.toJson(new Status(200));
    }
}
