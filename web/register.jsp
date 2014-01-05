<%-- 
    Document   : logIn
    Created on : Dec 27, 2013, 10:49:59 AM
    Author     : andrazhribernik
--%>

<!DOCTYPE html>
<html>
    <jsp:include page="templates/header.jsp" />
    <body>
        <jsp:include page="templates/navigationBar.jsp" />
        <div class="col-md-4 col-md-offset-4">
            <form role="form" method="POST" action="RegisterNewUser">
                <div class="form-group">
                  <label for="exampleInputEmail1">Email</label>
                  <input type="text" class="form-control" id="exampleInputEmail1" placeholder="i.e. jack@gmail.com" name="username" />
                  <% if(request.getParameter("usernameMessage") != null){ %>
                    <div class="alert alert-danger"><%=request.getParameter("usernameMessage") %></div>
                  <% } %>
                </div>
                <div class="form-group">
                  <label for="exampleInputPassword1">Password</label>
                  <input type="password" class="form-control" id="exampleInputPassword1" placeholder="Password" name="password" />
                  <% if(request.getParameter("passwordMessage") != null){ %>
                    <div class="alert alert-danger"><%=request.getParameter("passwordMessage") %></div>
                  <% } %>
                </div>
                <div class="form-group">
                  <label for="exampleInputPassword2">Re-type password</label>
                  <input type="password" class="form-control" id="exampleInputPassword2" placeholder="Re-type Password" name="repassword" />
                  <% if(request.getParameter("rePasswordMessage") != null){ %>
                    <div class="alert alert-danger"><%=request.getParameter("rePasswordMessage") %></div>
                  <% } %>
                </div>
                
                <button type="submit" class="btn btn-default">Register</button>
                
            </form>
        </div>
    </body>
</html>
