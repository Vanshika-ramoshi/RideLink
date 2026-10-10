<%@ page contentType="text/html;charset=UTF-8"%>

<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">

<title>Login - RideLink</title>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/style.css?v=3">

</head>

<body class="rl-auth-page">

	<header class="rl-auth-header">

		<a href="${pageContext.request.contextPath}/index.jsp" class="rl-logo">

			<span class="rl-logo-mark">R</span> RideLink

		</a>

		<div class="rl-auth-header-right">

			New to RideLink? <a
				href="${pageContext.request.contextPath}/register.jsp"> Create
				account </a>

		</div>

	</header>


	<main class="rl-auth-wrapper">

		<section class="rl-auth-intro">

			<div class="yellow-line"></div>

			<h1>
				Welcome<br> back.
			</h1>

			<p>Login to continue booking rides and managing your RideLink
				journey.</p>

			<div class="rl-auth-points">

				<div class="rl-auth-point">
					<span class="rl-check">✓</span> Smart driver matching
				</div>

				<div class="rl-auth-point">
					<span class="rl-check">✓</span> Distance-based fare
				</div>

				<div class="rl-auth-point">
					<span class="rl-check">✓</span> Simple ride management
				</div>

			</div>

		</section>


		<section class="rl-auth-form">

			<h2>Login</h2>

			<p class="rl-auth-form-subtitle">Enter your account details to
				continue.</p>


			<%
			if (request.getAttribute("error") != null) {
			%>

			<div class="rl-alert rl-alert-error">
				<%=request.getAttribute("error")%>
			</div>

			<%
			}
			%>


			<form action="${pageContext.request.contextPath}/login" method="post">

				<div class="rl-form-group">

					<label>Email address</label> <input type="email" name="email"
						class="rl-input" placeholder="Enter your email" required>

				</div>


				<div class="rl-form-group">

					<label>Password</label>

					<div class="rl-password-wrap">

						<input type="password" id="loginPassword" name="password"
							class="rl-input" placeholder="Enter your password" required>

						<button type="button" class="rl-password-toggle"
							data-target="loginPassword">Show</button>

					</div>

				</div>


				<button type="submit" class="rl-btn rl-btn-yellow rl-auth-submit">

					Login to RideLink</button>

			</form>


			<p class="rl-form-note">

				Don't have an account? <a
					href="${pageContext.request.contextPath}/register.jsp"> Create
					one </a>

			</p>

		</section>

	</main>


	<script src="${pageContext.request.contextPath}/js/script.js?v=3"></script>

</body>
</html>