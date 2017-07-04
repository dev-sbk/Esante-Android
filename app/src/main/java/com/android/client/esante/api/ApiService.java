package com.android.client.esante.api;
import com.android.client.esante.converter.ActeMedicaleConverter;
import com.android.client.esante.converter.ContactConverter;
import com.android.client.esante.converter.DocteurConverter;
import com.android.client.esante.converter.MaladieConverter;
import com.android.client.esante.converter.PatientConverter;
import com.android.client.esante.converter.RdvConverter;
import com.android.client.esante.converter.StringConverter;
import com.android.client.esante.converter.TraitementConverter;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface ApiService {
    @GET
    Call<PatientConverter> authentication(@Url String url);
    @FormUrlEncoded
    @POST("/esante/mspatient/login/")
    Call<DocteurConverter> authentication(@Field("email") String email,@Field("password") String password);
    @GET
    Call<ContactConverter> contacts(@Url String url);
    @GET
    Call<DocteurConverter> docteurs(@Url String url);
    @GET
    Call<PatientConverter> patients(@Url String url);
    @GET
    Call<MaladieConverter> getMaladiesByPatient(@Url String url);
    @FormUrlEncoded
    @POST("/esante/mspatient/rendezvous/")
    Call<RdvConverter> rdvs(@Field("id") String id,@Field("role") String key);
    @GET
    Call<TraitementConverter> getTraitementByPatient(@Url String url);
    @GET
    Call<ActeMedicaleConverter> getActeByPatient(@Url String url);
    @FormUrlEncoded
    @POST("/esante/mspatient/addrdv/")
    Call<RdvConverter>addRdv(@Field("dateRDV") String dateRDV,@Field("heureRDV") String heureRDV,
                                @Field("valide") int valide,
                                @Field("id_medecin") int id_medecin,
                                @Field("id_patient") int id_patient);
    @GET
    Call<DocteurConverter> profile(@Url String url);
    @FormUrlEncoded
    @POST("/esante/mspatient/updateprofiledocteur/")
    Call<StringConverter> updateprofile(@Field("id") String id,@Field("nom") String nom, @Field("prenom") String prenom, @Field("tel") String tel, @Field("email") String email );
    @FormUrlEncoded
    @POST("/esante/mspatient/registertoken/")
    Call<StringConverter> registerToken(@Field("token") String token,@Field("idPatient")int idPatient,@Field("idMedecin")int idMedecin);
}
