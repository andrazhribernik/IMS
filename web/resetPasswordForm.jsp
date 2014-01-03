<!DOCTYPE html>
<html>
    <jsp:include page="templates/header.jsp" />
    <body>
        <jsp:include page="templates/navigationBar.jsp" />
        <div class="col-md-4 col-md-offset-4">
            <form role="form" method="POST" action="ResetPasswordRequest">
                <div class="form-group">
                  <label for="exampleInputEmail1">Username</label>
                  <input type="text" class="form-control" id="exampleInputEmail1" placeholder="Enter username" name="username" />
                  <% if(request.getParameter("messageUsername") != null){ %>
                    <div class="alert alert-danger"><%=request.getParameter("messageUsername") %></div>
                  <% } %>
                </div>
                <div class="form-group">
                  <label for="exampleInputPassword1">Email</label>
                  <input type="email" class="form-control" id="exampleInputPassword1" placeholder="i.e. jack@gmail.com" name="email" />
                  <% if(request.getParameter("messageEmail") != null){ %>
                    <div class="alert alert-danger"><%=request.getParameter("messageEmail") %></div>
                  <% } %>
                </div>
                
                <button type="submit" class="btn btn-default">Get Password</button>
                <% if(request.getParameter("message") != null){ %>
                    <div class="alert alert-success"><%=request.getParameter("message") %></div>
                <% } %>
                
            </form>
            <div>
                Or <a href="./register.jsp">Sign up</a> for new account.
            </div>
        </div>
    </body>
</html>
