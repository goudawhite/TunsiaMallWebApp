package beans.crud;



import java.io.File;
import java.io.Serializable;


import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;

import com.esprit.entity.Client;
import com.esprit.entity.ShopOwner;
import com.esprit.entity.SuperAdmin;
import com.esprit.entity.Utilisateur;
import com.esprit.service.UserServiceLocal;

import utility.Iutility;
import utility.Utility;




@SessionScoped
@ManagedBean(name = "profileBean")
public class ProfileBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@EJB
	private UserServiceLocal<Utilisateur> serviceUser;




	/**
	 * ------------------------------------------------------------------------
	 * --------------------------------------------------------
	 */
	public Utilisateur utilisateur = new Utilisateur();
	public Utilisateur utilisateur2 = new Utilisateur();


	
	private String email;
	private String idQualie;

	private Boolean ok;
	Iutility ut =new Utility();

	private boolean superAdminISConnected;

	private boolean shopOwnerIsConnected;

	

	@PostConstruct
	public void initialization() {
	
		
		if(ut.getUserfromMapSession() instanceof ShopOwner){
		utilisateur = (Utilisateur) serviceUser.findById(new ShopOwner(), "id", ut.getUserfromMapSession().getId().toString()) ;
		shopOwnerIsConnected=true;
		}
		

		if(ut.getUserfromMapSession() instanceof SuperAdmin){
		utilisateur = (Utilisateur) serviceUser.findById(new SuperAdmin(), "id", ut.getUserfromMapSession().getId().toString()) ;
		superAdminISConnected=true;
		}
		
		if(ut.getUserfromMapSession() instanceof Client)
			utilisateur = (Utilisateur) serviceUser.findById(new Client(), "id", ut.getUserfromMapSession().getId().toString()) ;
		
	}
	
	public String howIsConnected(){
		
		if(ut.getUserfromMapSession() instanceof ShopOwner){
			return "shopOwner";
		}
		if(ut.getUserfromMapSession() instanceof SuperAdmin){
			return "superAdmin";
			}
		if(ut.getUserfromMapSession() instanceof Client){
			return "client";
			}
		return null;
	}

	public void fileUpload22(FileUploadEvent event) throws Exception {
		Iutility traitementImgText =new Utility(); 
		
		
		String path = FacesContext.getCurrentInstance().getExternalContext()
				.getRealPath("/");
		
		File file=traitementImgText.writeFile(event, "PublicImage/");
		
	
	
		
		
		utilisateur.setUrlPhoto(file.getName());
		// getNosFormulaire().setUrlPhotot("thumb"+file.getName());
		
		serviceUser.update(utilisateur);
		 initialization();
		FacesMessage msg = new FacesMessage("chargement avec succ�s ", event
				.getFile().getFileName() + " is uploaded.");

		FacesContext.getCurrentInstance().addMessage(null, msg);


	}


	public void adding(Utilisateur user) {



		try {
	
			serviceUser.create(user);
			initialization();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Enregistr�(e) avec succ�s", "");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (Exception e) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Email existe d�ja !", "");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		
		}
	}




	/*----------------------------------------------------------------------------------------------------------*/
	public void updating(Utilisateur u) {
	
			serviceUser.update(u);
		
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"mis(e) � jour avec succ�s", "");
			FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	/*----------------------------------------------------------------------------------------------------------*/
	public void removing(Utilisateur user) {
		try {

			serviceUser.delete(user);

		
			initialization();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Supprim�(e) avec succ�s", "");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (Exception e) {
		
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Vous ne pouvez pas le supprimer Contacter l'adminisatrateur Syst�me ", "");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}

	}

	/**
	 * ------------------------------------------------------------------------
	 * --------------------------------------------------------
	 */


	/**
	 * ------------------------------------------------------------------------
	 * --------------------------------------------------------
	 */
	

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIdQualie() {
		return idQualie;
	}

	public void setIdQualie(String idQualie) {
		this.idQualie = idQualie;
	}

	public Boolean getOk() {
		return ok;
	}

	public void setOk(Boolean ok) {
		this.ok = ok;
	}

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	public Utilisateur getUtilisateur2() {
		return utilisateur2;
	}

	public void setUtilisateur2(Utilisateur utilisateur2) {
		this.utilisateur2 = utilisateur2;
	}


	public Iutility getUt() {
		return ut;
	}


	public void setUt(Iutility ut) {
		this.ut = ut;
	}


	public void setServiceUser(UserServiceLocal<Utilisateur> serviceUser) {
		this.serviceUser = serviceUser;
	}

	public boolean isSuperAdminISConnected() {
		return superAdminISConnected;
	}

	public void setSuperAdminISConnected(boolean superAdminISConnected) {
		this.superAdminISConnected = superAdminISConnected;
	}

	public boolean isShopOwnerIsConnected() {
		return shopOwnerIsConnected;
	}

	public void setShopOwnerIsConnected(boolean shopOwnerIsConnected) {
		this.shopOwnerIsConnected = shopOwnerIsConnected;
	}
}