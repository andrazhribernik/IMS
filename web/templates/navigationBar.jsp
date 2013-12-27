<%-- 
    Document   : navigationBar
    Created on : Nov 3, 2013, 11:33:57 AM
    Author     : andrazhribernik
--%>

<%@page import="ims.entity.Role"%>
<%@page import="ims.management.LoginManagement"%>
<nav class="navbar navbar-inverse" role="navigation">
    <div class="navbar-header">  
        <a class="navbar-brand">Image management system </a>
    </div>
    <div class="">
        <ul class="nav navbar-nav">
        <% 
            LoginManagement loginMng = new LoginManagement(session);
            if(loginMng.isLoggedIn()){
                Role userRole = loginMng.getUser().getRoleidRole();
                if(userRole.getName().equals("user")){ %>
                    <jsp:include page="./userNavigationBar.jsp" />
                <%}
                else if(userRole.getName().equals("admin")){
                %>
                    <jsp:include page="./adminNavigationBar.jsp" />
                <%}
                else if(userRole.getName().equals("contributor")){%>
                    <jsp:include page="./contributorNavigationBar.jsp" />
                <%}
            }
            else{ %>
                <li><a href="index.jsp">Images</a></li>
            <%}
        %>
        </ul>
        <ul class="nav navbar-nav navbar-right">
            <%
            if(loginMng.isLoggedIn()){ %>
            
                <li><a href="#"><%=loginMng.getUser().getUsername() %></a></li>
                
                <li>
                    <form action="LogOutServlet" method="POST">
                        <button type="submit" class="btn btn-default navbar-btn" name="logout" value="logout">Log out</button>
                    </form>
                </li>
            <%}
            else{ %>
                <li><a href="logIn.jsp">Log in</a></li>
            <%}
            %>
             
        </ul>    
    </div>
</nav>