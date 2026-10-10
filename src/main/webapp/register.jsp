<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">

<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>RideLink - Create Account</title>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/style.css?v=5">

</head>

<body class="rl-auth-page">


	<!-- HEADER -->

	<header class="rl-auth-header">

		<a href="${pageContext.request.contextPath}/" class="rl-logo"> <span
			class="rl-logo-mark">R</span> RideLink

		</a>


		<div class="rl-auth-header-right">

			Already have an account? <a
				href="${pageContext.request.contextPath}/login.jsp"> Login </a>

		</div>

	</header>



	<!-- MAIN -->

	<main class="rl-auth-wrapper">


		<!-- LEFT SIDE -->

		<section class="rl-auth-intro">

			<div class="yellow-line"></div>

			<h1>Move smarter. Ride better.</h1>

			<p>Create your RideLink account and enjoy simple, reliable and
				smart ride booking.</p>


			<div class="rl-auth-points">

				<div class="rl-auth-point">

					<span class="rl-check">✓</span> Easy ride booking

				</div>


				<div class="rl-auth-point">

					<span class="rl-check">✓</span> Transparent fare calculation

				</div>


				<div class="rl-auth-point">

					<span class="rl-check">✓</span> Multiple vehicle options

				</div>


				<div class="rl-auth-point">

					<span class="rl-check">✓</span> Secure ride history

				</div>

			</div>

		</section>



		<!-- REGISTER FORM -->

		<section class="rl-auth-form">

			<h2>Create your account</h2>

			<p class="rl-auth-form-subtitle">Join RideLink and start your
				journey.</p>


			<!-- ERROR -->

			<%
			String error = (String) request.getAttribute("error");

			if (error != null) {
			%>

			<div class="rl-alert rl-alert-error">
				<%=error%>
			</div>

			<%
			}
			%>


			<form action="${pageContext.request.contextPath}/register"
				method="post">


				<!-- NAME -->

				<div class="rl-form-group">

					<label for="name"> Full Name </label> <input type="text" id="name"
						name="name" class="rl-input" placeholder="Enter your full name"
						required>

				</div>


				<!-- EMAIL -->

				<div class="rl-form-group">

					<label for="email"> Email Address </label> <input type="email"
						id="email" name="email" class="rl-input"
						placeholder="Enter your email" required>

				</div>


				<!-- PHONE -->

				<div class="rl-form-group">

					<label for="phone"> Phone Number </label> <input type="tel"
						id="phone" name="phone" class="rl-input"
						placeholder="Enter your phone number" maxlength="15" required>

				</div>


				<!-- PASSWORD -->

				<div class="rl-form-group">

					<label for="password"> Password </label>

					<div class="rl-password-wrap">

						<input type="password" id="password" name="password"
							class="rl-input" placeholder="Create a password" required>

						<button type="button" class="rl-password-toggle"
							onclick="togglePassword('password', this)">SHOW</button>

					</div>

				</div>


				<!-- CONFIRM PASSWORD -->

				<div class="rl-form-group">

					<label for="confirmPassword"> Confirm Password </label>

					<div class="rl-password-wrap">

						<input type="password" id="confirmPassword" name="confirmPassword"
							class="rl-input" placeholder="Confirm your password" required>

						<button type="button" class="rl-password-toggle"
							onclick="togglePassword('confirmPassword', this)">SHOW</button>

					</div>

				</div>


				<!-- TERMS -->

				<p class="rl-form-note">By creating an account, you agree to use
					RideLink responsibly and provide accurate information.</p>


				<!-- SUBMIT -->

				<button type="submit" class="rl-btn rl-btn-yellow rl-auth-submit">

					Create Account</button>


			</form>


			<!-- LOGIN -->

			<p class="rl-form-note">

				Already have an account? <a
					href="${pageContext.request.contextPath}/login.jsp"
					style="color: #111; font-weight: 800;"> Login here </a>

			</p>

		</section>

	</main>



	<!-- PASSWORD SCRIPT -->

	<script>
		function togglePassword(inputId, button) {

			const input = document.getElementById(inputId);

			if (input.type === "password") {

				input.type = "text";

				button.textContent = "HIDE";

			} else {

				input.type = "password";

				button.textContent = "SHOW";
			}
		}
	</script>

</body>
</html>