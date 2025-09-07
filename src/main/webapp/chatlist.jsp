<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<%@ page import="logic.Chat, logic.Message, java.util.ArrayList, java.util.List" %>

<title>
	Chats
</title>
</head>
<body>
	<div style="display: flex; width: 80vw; height: 80vh; background-color: grey; margin-top: 10vh; margin-bottom: 10vh; margin-left: 10vw; margin-right: 10vw; border-radius: 10px; align-items: center; justify-content: center;">
		<div style="display: block; width: 90%; height: 90%; background-color: grey; z-index: 1; border-radius: 5px;">
			<div style="display: block; width: 100%; height: 100%; border-radius: 5px;">
				<div style="display: block; width: 100%; height: 90%; justify-self: center; background-color: white; border-radius: 5px;">
					<%
						Chat chat = new Chat();
						int user_id = (int) session.getAttribute("current_user_id");
						chat.setUser_id(user_id);
						
						ArrayList<Message> messages = chat.getMessages();
						List<ArrayList<Object>> dialogs = chat.getDialogs(messages);
						
						session.setAttribute("Chat", chat);
					%>
					<%= chat.showDialogs(dialogs) %>
				</div>
				<div style="display: block; width: 100%; height: 10%; background-color: grey; z-index: 2; border-radius: 5px; justify-self: center;">
					<input type="text" name="message_body" style="display: block; width: 99%; height: 50%; border-radius: 5px; justify-self: center;">
				</div>
			</div>
		</div>
		
	</div>
</body>
</html>