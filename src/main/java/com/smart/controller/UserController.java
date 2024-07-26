/*package com.smart.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.smart.entities.User;
import com.smartdao.UserRepository;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
	
	@RequestMapping("/index")
	 public String dashboard(Model model,Principal principal)
	 {
		String userName=principal.getName();
		System.out.println("USERNAME"+ userName);
		 
		//get the user using username(Email)
		
		User user=userRepository.getUserByUserName(userName);
		System.out.println("USER"+user);
		
		model.addAttribute("User", user);
		

		return"normal/user_dashboard"; 
	 }
}*/

/*package com.smart.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.smart.entities.Contact;
import com.smart.entities.User;
import com.smart.helper.Message;
import com.smartdao.UserRepository;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {
    
    @Autowired
    private UserRepository userRepository;
    
    //Method for adding common data  to response
    @ModelAttribute
    public void  addCommonData(Model model,Principal principal) {
    	String userName = principal.getName();
        System.out.println("USERNAME: " + userName);
        
        // Get the user using username (Email)
        User user = userRepository.getUserByUserName(userName);
        System.out.println("USER: " + user);
        
        model.addAttribute("user", user); // Changed to lowercase "user"
        	
    }
    
    //Dashboard home
    @RequestMapping("/index")
    public String dashboard(Model model, Principal principal) {
    	model.addAttribute("title","User Dashboard");
        return "normal/user_dashboard"; 
    }
    
    //open add form handler
    @GetMapping("/add-contact")
    public String openAddContactForm(Model model) {
    	model.addAttribute("title","Add Contact");
    	model.addAttribute("contact",new Contact());
    	return"normal/add_contact_form";
    }
    
    //Processing add contact form
    @PostMapping("/process-contact")
     public String processContact(@ModelAttribute Contact contact,
    		 @RequestParam("profileImage") MultipartFile file,
    		 Principal principal,HttpSession session) {
    	
    	try {
    	String name=principal.getName();
    	User user=this.userRepository.getUserByUserName(name);
    	  
    	//Processing and uploading file...
    	 if (file.isEmpty()) {
    		 //if the file is empty then try our message
    	
    	 }
    	 else {
    		 //file the file to the folder and update the name to contact
    	 contact.setImage(file.getOriginalFilename());
    	 
    	 File saveFile=new ClassPathResource("static/img").getFile();
    	 
    	  Path path = Paths.get(saveFile.getAbsolutePath()+File.separator+file.getOriginalFilename());	
    	 
    	 Files.copy(file.getInputStream(),path,StandardCopyOption.REPLACE_EXISTING );
    	 }
    	 
    	 
    	 
    	contact.setUser(user);
       	user.getContacts().add(contact);
    	
    	this.userRepository.save(user);
    	
    	System.out.println("DATA"+contact);
    	
    	System.out.println("Added to database");
    	
    	//Message Success.....
    	session.setAttribute("message",new Message("Your contact is added!! Add more...","success"));
    	}
    	
    	catch(Exception e){
    		
    		System.out.println("Error"+e.getMessage());
    		e.printStackTrace();
    		
    		//Error Message...
        	session.setAttribute("message",new Message("Something went wrong!! Try again...","danger"));

    	}
    	
    	
    	return"normal/add_contact_form";
    	 
     }
}*/

/*package com.smart.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.smart.entities.Contact;
import com.smart.entities.User;
import com.smart.helper.Message;
import com.smartdao.UserRepository;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {
    
    @Autowired
    private UserRepository userRepository;
    
    // Method for adding common data to response
    @ModelAttribute
    public void addCommonData(Model model, Principal principal) {
        String userName = principal.getName();
        System.out.println("USERNAME: " + userName);
        
        // Get the user using username (Email)
        User user = userRepository.getUserByUserName(userName);
        System.out.println("USER: " + user);
        
        model.addAttribute("user", user);
    }
    
    // Dashboard home
    @RequestMapping("/index")
    public String dashboard(Model model, Principal principal) {
        model.addAttribute("title", "User Dashboard");
        return "normal/user_dashboard"; 
    }
    
    // Open add form handler
    @GetMapping("/add-contact")
    public String openAddContactForm(Model model) {
        model.addAttribute("title", "Add Contact");
        model.addAttribute("contact", new Contact());
        return "normal/add_contact_form";
    }
    
    // Processing add contact form
    @PostMapping("/process-contact")
    public String processContact(@ModelAttribute Contact contact,
                                 @RequestParam("profileImage") MultipartFile file,
                                 Principal principal, HttpSession session) {
        try {
            String name = principal.getName();
            User user = this.userRepository.getUserByUserName(name);
            
            // Processing and uploading file...
            if (file.isEmpty()) {
                // if the file is empty then try our message
                System.out.println("File is empty");
            } else {
                // Upload the file to the folder and update the name to contact
                contact.setImage(file.getOriginalFilename());
                
                File saveFile = new ClassPathResource("static/img").getFile();
                
                Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());
                
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            }
            
            contact.setUser(user);
            user.getContacts().add(contact);
            
            this.userRepository.save(user);
            
            System.out.println("DATA: " + contact);
            System.out.println("Added to database");
            
            // Success message
            session.setAttribute("message", new Message("Your contact is added!! Add more...", "success"));
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
            
            // Error message
            session.setAttribute("message", new Message("Something went wrong!! Try again...", "danger"));
        }
        
        return "normal/add_contact_form";
    }
    
    //show contacts handler
    
    @GetMapping("/show-contacts")
    public String showContacts(Model m) {
        return"normal/show_contacts";
    }
}*/


package com.smart.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.smart.entities.Contact;
import com.smart.entities.User;
import com.smart.helper.Message;
import com.smartdao.ContactRepository;
import com.smartdao.UserRepository;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepository userRepository;
    	
    @Autowired
    private ContactRepository contactRepository;

	private User userByUserName;
    
    @ModelAttribute
    public void addCommonData(Model model, Principal principal) {
        String userName = principal.getName();
        System.out.println("USERNAME: " + userName);
        User user = userRepository.getUserByUserName(userName);
        System.out.println("USER: " + user);
        model.addAttribute("user", user);
    }

    @RequestMapping("/index")
    public String dashboard(Model model, Principal principal) {
        model.addAttribute("title", "User Dashboard");
        return "normal/user_dashboard";
    }

    @GetMapping("/add-contact")
    public String openAddContactForm(Model model) {
        model.addAttribute("title", "Add Contact");
        model.addAttribute("contact", new Contact());
        return "normal/add_contact_form";
    }

   
    @PostMapping("/process-contact")
    public String processContact(@ModelAttribute Contact contact,
                                 @RequestParam(name = "profileImage", required = false) MultipartFile file,
                                 Principal principal, HttpSession session) {
        try {
            String name = principal.getName();
            User user = this.userRepository.getUserByUserName(name);

            // Check if file is empty or null
            if (file == null || file.isEmpty()) {
                contact.setImage("contact.png"); // Set default image filename
            } else {
                contact.setImage(file.getOriginalFilename());
                File saveFile = new ClassPathResource("static/img").getFile();
                Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            }

            contact.setUser(user);
            user.getContacts().add(contact);
            this.userRepository.save(user);

            System.out.println("DATA: " + contact);
            System.out.println("Added to database");

            session.setAttribute("message", new Message("Your contact is added!! Add more...", "success"));
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
            session.setAttribute("message", new Message("Something went wrong!! Try again...", "danger"));
        }
        return "normal/add_contact_form";
    }

    
    
    
    //Show contacts handler
    //Per page=5[n]
    //Current page=0 [page]
    	
    @GetMapping("/show-contacts/{page}")
    public String showContacts(@PathVariable("page") Integer page,Model model,Principal principal) {
        model.addAttribute("title", "Show User Contacts");
         //Contact List
        
        	String userName = principal.getName();
        	
        	User user=this.userRepository.getUserByUserName(userName);
        	
        	//currentPage-page
        	//contact per page 3
        	PageRequest pageable =PageRequest.of(page,6);
        	
        	Page<Contact> contacts = this.contactRepository.findContactsByUser(user.getId(),pageable);
        	
        	model.addAttribute("contacts", contacts);
        	model.addAttribute("currentPage",page);
        	model.addAttribute("totalPages",contacts.getTotalPages());
        	
        	return "normal/show_contacts";
    }
    
    //showing Particular contact details
    @RequestMapping("/contact/{cId}")
    public String showContactDetail(@PathVariable("cId") Integer cId,Model model,Principal principal) {
    	System.out.println("CID" +cId);
    	
    	Optional<Contact> contactOptional = this.contactRepository.findById(cId);
    	Contact contact = contactOptional.get();
    	
    	String userName = principal.getName();
    	User user = this.userRepository.getUserByUserName(userName);
    	
    	if(user.getId()==contact.getUser().getId()) {
    	model.addAttribute("contact",contact);
    	model.addAttribute("title",contact.getName());
    	}
    	
    	return "normal/contact_detail";
    }
    
    //delete contact handler
    @GetMapping("/delete/{cid}")
    public String deleteContact(@PathVariable("cid")Integer cId,Model model,HttpSession session) {
    System.out.println("CID"+cId);
    	
    	Contact contact =this.contactRepository.findById(cId).get();
    	
    	//check..
    	System.out.println("Contact" +contact.getcId());
    	
    	contact.setUser(null);
    	
    	//Remove
    	//img
    	//contact.getImage()
    	
    	this.contactRepository.delete(contact);
    	
    	System.out.println("DELETED");
    	session.setAttribute("message",new Message("Contact deleted succesfully..","success"));
    	return"redirect:/user/show-contacts/0";
    }
    
    
    //open update form handler
    @PostMapping("/update-contact/{cid}")
    public String updateForm(@PathVariable("cid") Integer cid,Model m) {
    	
    	m.addAttribute("title","Update Contact");
    	Contact contact = this.contactRepository.findById(cid).get();
    	m.addAttribute("contact",contact);
    	return"normal/update_form";
    }
    
    //Update contact handler
    @RequestMapping(value="/process-update",method=RequestMethod.POST)
    public String updateHandler(@ModelAttribute Contact contact,@RequestParam("profileImage") MultipartFile file,Model m,HttpSession session,Principal principal) {
    	
    	try {
			//old contact details
    		Contact oldcontactDetail = this.contactRepository.findById(contact.getcId()).get();
    		//image..
    		
    		if(!file.isEmpty()) {
    			//file work... 
    			//rewrite
    			
    			//delete old photo
    			File deleteFile = new ClassPathResource("static/img").getFile();
    			File file1=new File(deleteFile,oldcontactDetail.getImage());
    			file1.delete();
    			
    			//Update new photo
    			 File saveFile = new ClassPathResource("static/img").getFile();
                 Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());
                 Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                 contact.setImage(file.getOriginalFilename());
    		}
    		
    		else {
    			contact.setImage(oldcontactDetail.getImage());
    		}
    			User user=userRepository.getUserByUserName(principal.getName());
    			contact.setUser(user);
    			this.contactRepository.save(contact);
    			
    			session.setAttribute("message",new Message("Your Contact is Updated..","success"));
		} catch (Exception e) {
			// TODO: handle exception
		}
    	
    	
    	System.out.println("CONTACT NAME" +contact.getName());
    	System.out.println("CONTACT ID" +contact.getcId());
    	return "redirect:/user/contact/" + contact.getcId();

    }
    
    
    //Your profile handler
    @GetMapping("/profile")
    public String yourProfile(Model model) {
    	model.addAttribute("title","Profile Page");
    	return"normal/profile";
    }
    
    //Open setting handler
    @GetMapping("/settings")
    public String openSetting() {
    	return"normal/settings";
    }
    
    //Change password.. handler
    @PostMapping("/change-password")
    public String changePassword(@RequestParam("oldPassword") String oldPassword,@RequestParam("newPassword") String newPassword,Principal principal,HttpSession session) {
    	System.out.println("OLDPASSWORD" + oldPassword);
    	System.out.println("NEWPASSWORD" + newPassword);
    		
    	String userName = principal.getName();
    	User currentUser = this.userRepository.getUserByUserName(userName);
    	System.out.println(currentUser.getPassword());
    	
    	
    	if(this.bCryptPasswordEncoder.matches(oldPassword, currentUser.getPassword())) {
    		//change the password
    		currentUser.setPassword(this.bCryptPasswordEncoder.encode(newPassword));
    		this.userRepository.save(currentUser);
			session.setAttribute("message",new Message("Your password  is successfully changed..","success"));

    		
    	}else {
    		//error....
			session.setAttribute("message",new Message("Please Enter correct old password !!","danger"));
			return"redirect:/user/settings";
    	}
    	
    	return"redirect:/user/index";
    }
}
    
