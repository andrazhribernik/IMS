<%@page import="ims.management.LoginManagement"%>
<%@page import="ims.entity.Image"%>
<%@page import="java.util.List"%>
<%@page import="ims.management.ImageManagement"%>
<!--
To change this template, choose Tools | Templates
and open the template in the editor.
-->
<!DOCTYPE html>
<html>
    <jsp:include page="templates/header.jsp" />
    <body>
        <jsp:include page="templates/navigationBar.jsp" />
        <div class="row">
            <div class="col-md-8 col-md-offset-2 text-center">
        <%  
            String idImage="";
            try{
                idImage =request.getParameter("imageId");
            }
            catch(Exception e){
                idImage = null;
            }
            ImageManagement im = new ImageManagement();
            Image currentImage = im.getImageById(idImage);
            
            String url = request.getRequestURL().toString();
            String baseURL = url.substring(0, url.length() - request.getRequestURI().length()) + request.getContextPath() + "/";
            
            if(currentImage == null){
            %>
                <div class="alert alert-danger">Wrong parameter.</div>
            
            <%
            }
            else{
        %>
        
                <img src="GetImage?imageId=<%=currentImage.getIdImage()%>&size=600"  class="img-thumbnail" />
                <br>
                <br>
                
                <div>
                    
                    <div class="panel panel-default">
                        <div class="panel-heading">
                          <h3 class="panel-title"><%=currentImage.getName() %></h3>                        
                        </div>
                        <div class="panel-body">
                            <dl class="dl-horizontal">
                                <dt>Price</dt>
                                <dd>&euro; <%=currentImage.getPriceD()%></dd>
                                <dt>Items sold</dt>
                                <dd><%=im.soldHowManyTimes(currentImage) %></dd>
                            </dl>
                        </div>
                        <div class="panel-footer">
                            <%
                                LoginManagement lm = new LoginManagement(session);
                                if(lm.isLoggedIn()){
                            %>
                            <form action="https://www.sandbox.paypal.com/cgi-bin/webscr" method="post" target="_top">
                                <input type="hidden" name="cmd" value="_xclick">
                                <input type="hidden" name="business" value="andraz.hribernik-facilitator@gmail.com">
                                <input type="hidden" name="lc" value="SI">
                                <input type="hidden" name="item_name" value="CMS full image size">
                                <input type="hidden" name="item_number" value="<%=currentImage.getIdImage()%>">
                                <input type="hidden" name="amount" value="<%=currentImage.getPriceD().toString()%>">
                                <input type="hidden" name="currency_code" value="EUR">
                                <input type="hidden" name="button_subtype" value="services">
                                <input type="hidden" name="no_note" value="0">
                                <input type="hidden" name="tax_rate" value="0.000">
                                <input type="hidden" name="shipping" value="0.00">
                                <input type="hidden" name="bn" value="PP-BuyNowBF:btn_buynowCC_LG.gif:NonHostedGuest">
                                <input type="image" src="https://www.paypalobjects.com/en_US/i/btn/btn_buynowCC_LG.gif" border="0" name="submit" alt="PayPal - The safer, easier way to pay online!">
                                <img alt="" border="0" src="https://www.paypalobjects.com/en_US/i/scr/pixel.gif" width="1" height="1">
                                <input type="hidden" name="return" value="<%=baseURL %>conformationOfPurchase"/>
                            </form>
                            <%} else{%>
                            <a href="./logIn.jsp"><img alt="" border="0" src="https://www.paypalobjects.com/en_US/i/btn/btn_buynowCC_LG.gif"></a>
                            <%} %>
                        </div>    
                    </div>
                    
                    
                </div>

            <%  
                }
            %>
            </div>
        </div>
            

    </body>
</html>
