<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
	<script src="jquery-2.1.1.js"></script>
	<script>
		$(document).ready(function(){
			$("button.delete").click(function(){
				// when the delete button is clicked, we want to send the id of the album to the server
				// we do this via the hidden form below (#deleteform)
				
				// get the album id where del button was clicked
				var id = $(this).attr("data-id");
				
				// assign id to input iddelete
				$("input#iddelete").val(id);
				// submit the form
				$("form#deleteform").submit();
			});
			
			// we use "$(document).on("click", "button.update", function(){})" because we want buttons who 
			// dynamically got this class ("update") to also have this click listener 
			// in class, I forgot to do .on and instead used .click. We have to use .on to allow the case of a button returning to the class "update"
			$(document).on("click", "button.update", function(e){
				// when the update the button is clicked, we want to allow the user to edit the current album details
				// But first 
				
				// get the id of this update button
				var id = $(this).attr("data-id");
				
				// make all p.pe-<id> content editable
				// this will serve as our selector
				var selector = "p.pe-" + id;

				// let's make the current p elements editable
				$(selector).attr("contenteditable", "true");
				
				// we will change the button's class from update to save
				// this way, we can easily control its behavior if it belongs to the class "update", or to class "save"
				$(this).text("Save");
				$(this).removeClass("update");
				$(this).addClass("save");
				
			});
			
			// we use "$(document).on("click", "button.save", function(){})" because we want buttons who 
			// dynamically got this class ("save") to also have this click listener 
			$(document).on("click", "button.save", function(){
				// when the save button is clicked, we want to send the id of the album to the server with the new details
				// we do this via the hidden form below (#updateform)
				
				// id and the new album details
				var id = $(this).attr("data-id");
				
				// all of the selectors for inputs with this id starts with "p.pe-<id>
				// we'll use a base selector instead and then add the classes later
				var selector = "p.pe-" + id;

				// get the inputs using the base selector 
				var name = $(selector+".name").text();
				var desc = $(selector+".description").text();
				var priv = $(selector+".privacy").text();
				
				// set input values of the #updateform
				$("input#name").val(name);
				$("input#description").val(desc);
				$("input#privacy").val(priv);
				
				// submit form
				$("form#updateform").submit();
				
				// put our button back to normal, with its original class and text
				$(this).removeClass("save");
				$(this).addClass("update");
				$(this).text("Update");
				
				// make the input types non-editable again
				$(selector).attr("contenteditable", false);
			});
			
			// a sample ajax call to our server
			$("button.ajax").click(function(){
				$.ajax({
					"url" : "ajax",
					"method" : "post",
					"dataType" : "json",
					"success" : function(result){
						alert(result.name);
					}
				});
			});
		});
	</script>
</head>
<body>
	<button class="ajax">AJAX</button>

	<form action="addalbum" method="POST">
		Name: <input type="text" name="name" > <br>
		Description: <input type="text" name="description" > <br>
		Privacy: 
			<input type="radio" name="privacy" value="true">
				Private
			<input type="radio" name="privacy" value="false">
				Public
			<br>
		<input type="submit" >
	</form>

	<c:forEach items="${albums}" var ="a">
		<div>
			<p class="pe-${a.id} name">${a.name}</p>
			<p class="pe-${a.id} description">${a.description}</p> 
			<p class="pe-${a.id} privacy">${a.privacy}</p> 
			
			<button class="delete" data-id="${a.id}">Delete</button>
			<button class="update" data-id="${a.id}">Update</button>
		</div>
	</c:forEach>
	
	<form method="POST" action="deletealbum" id="deleteform">
		<input type="hidden" name="id" id="iddelete" />
	</form>
	
	<form method="POST" action="updatealbum" id="updateform">
		<input type="hidden" name="id" id="idupdate" />
		<input type="hidden" name="name" id="name" />
		<input type="hidden" name="description" id="description" />
		<input type="hidden" name="privacy" id="privacy" />
	</form>
	
	
</body>
</html>



