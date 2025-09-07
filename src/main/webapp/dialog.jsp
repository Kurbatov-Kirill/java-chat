<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<%@ page import="logic.Chat, java.util.ArrayList, logic.Message, logic.Dialog" %>
<title>
	<%
		String username = "";
		if(session.getAttribute("current_user") == null) {
			username = "GUEST";
		}
		else {
			username = (String) session.getAttribute("current_user");
		}
		
		Chat chat = (Chat) session.getAttribute("Chat");
		Dialog dialog = new Dialog();
		dialog.setCurrentDialogCompanionId(Integer.parseInt(request.getParameter("uid")));
		
		int chat_companion_id = Integer.parseInt(request.getParameter("uid"));
		request.setAttribute("msgReceiverId", chat_companion_id);
		ArrayList<Object> currentChatData = chat.getCurrentChatMessages(chat_companion_id);
		
		String chat_companion_name = (String) currentChatData.get(0);
		dialog.setCurrentDialogCompanionUsername(chat_companion_name);
		session.setAttribute("currentDialog", dialog);
		
		ArrayList<Message> chatMessages = (ArrayList<Message>) currentChatData.get(1);
	%>
	
	<%= "Chat with " + chat_companion_name %>
</title>
</head>
<body>
	<div style="display: flex; width: 80vw; height: 80vh; background-color: grey; margin-top: 10vh; margin-bottom: 10vh; margin-left: 10vw; margin-right: 10vw; border-radius: 10px; align-items: center; justify-content: center;">
		<div style="display: block; width: 90%; height: 90%; background-color: grey; z-index: 1; border-radius: 5px;">
			<div style="display: block; width: 100%; height: 100%; border-radius: 5px;">
				<div style="display: block; width: 100%; height: 90%; justify-self: center; background-color: white; border-radius: 5px;">
					<%= chat.displayChatMessages(chatMessages) %>
				</div>
				<div style="display: flex; width: 100%; height: 10%; background-color: grey; z-index: 2; border-radius: 5px; justify-self: center;">
					<form method="post" action="/TestTomcat/sendMessage" style="display: flex; width: 100%; height: 50%; border-radius: 5px; justify-self: center;">
						<input type="text" name="message_body" style="display: block; width: 100%; height: 80%; border-radius: 5px; justify-self: center;">
						<input type="submit" name="action" value="Send" style="display: block; width: 11%; height: 100%; border-radius: 5px; justify-self: center;">
					</form>
				</div>
			</div>
		</div>
		
	</div>
</body>
</html>