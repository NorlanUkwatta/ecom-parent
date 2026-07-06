<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Redirecting to Secure Payment...</title>
    <script type="text/javascript">
        window.onload = function() {
            document.getElementById('payhere-form').submit();
        }
    </script>
    <style>
        body { font-family: sans-serif; display: flex; justify-content: center; align-items: center; height: 100vh; background: #f8f9fa; }
        .loader { text-align: center; }
        .spinner { border: 4px solid #f3f3f3; border-top: 4px solid #0d6efd; border-radius: 50%; width: 40px; height: 40px; animation: spin 1s linear infinite; margin: 0 auto 20px; }
        @keyframes spin { 0% { transform: rotate(0deg); } 100% { transform: rotate(360deg); } }
    </style>
</head>
<body>

<div class="loader">
    <div class="spinner"></div>
    <h2>Redirecting to Secure Payment Gateway...</h2>
    <p>Please do not close or refresh this window.</p>
</div>

<form id="payhere-form" method="post" action="https://sandbox.payhere.lk/pay/checkout" style="display: none;">
    <input type="hidden" name="merchant_id" value="${merchantId}">
    <input type="hidden" name="return_url" value="http://localhost:8080${pageContext.request.contextPath}/payment/success">
    <input type="hidden" name="cancel_url" value="http://localhost:8080${pageContext.request.contextPath}/payment/cancel">
    <input type="hidden" name="notify_url" value=" https://71fd-2402-d000-a500-6bba-398b-3182-167f-67c0.ngrok-free.app${pageContext.request.contextPath}/payhere/notify">

    <input type="hidden" name="order_id" value="${orderId}">
    <input type="hidden" name="items" value="TechMart Order #${orderId}">
    <input type="hidden" name="currency" value="LKR">
    <input type="hidden" name="amount" value="${amount}">
    <input type="hidden" name="hash" value="${hash}">

    <input type="hidden" name="first_name" value="${firstName}">
    <input type="hidden" name="last_name" value="${lastName}">
    <input type="hidden" name="email" value="${email}">
    <input type="hidden" name="phone" value="${mobile}">
    <input type="hidden" name="address" value="${address}">
    <input type="hidden" name="city" value="Sri Lanka">
    <input type="hidden" name="country" value="Sri Lanka">
</form>

</body>
</html>