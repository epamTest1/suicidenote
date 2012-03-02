<%@page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<!doctype html>
<!--[if lt IE 7]><html class="no-js lt-ie9 lt-ie8 lt-ie7" lang="en"><![endif]-->
<!--[if IE 7]><html class="no-js lt-ie9 lt-ie8" lang="en"><![endif]-->
<!--[if IE 8]><html class="no-js lt-ie9" lang="en"><![endif]-->
<!--[if gt IE 8]><!--><html class="no-js" lang="en"><!--<![endif]-->
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">

	<title>Suicide Note</title>
	<meta name="description" content="">
	<meta name="author" content="">

	<meta name="viewport" content="width=device-width">

	<link rel="stylesheet/less" href="less/style.less">
	<script src="js/libs/less-1.2.1.min.js"></script>

	<link type="text/css" rel="stylesheet" href="css/libs/jquery-ui-1.8.18/snote-theme/jquery-ui-1.8.18.css">
	<link type="text/css" rel="stylesheet" href="css/libs/jquery-ui-1.8.18/snote-theme/jquery-ui-timepicker-addon.css">

	<!--
	<link rel="stylesheet/less" href="less/style.css">
	-->

	<script src="js/libs/modernizr-2.5.3.min.js"></script>

	<script type="text/javascript" src="http://use.typekit.com/qkc0yoa.js"></script>
	<script type="text/javascript">try{Typekit.load();}catch(e){}</script>
</head>
<body>
	<!--[if lt IE 7]><p class=chromeframe>Your browser is <em>ancient!</em> <a href="http://browsehappy.com/">Upgrade to a different browser</a> or <a href="http://www.google.com/chromeframe/?redirect=true">install Google Chrome Frame</a> to experience this site.</p><![endif]-->

	<div class="container">

		<section class="section_hello" role="main">

			<blockquote class="quote tk-acta-display" data-ajax-url="http://suicidenote.jelastic.servint.net/advice/index.json" data-interval-value="5sec">
				<p class="quote-text">
					<jsp:include page="/advice/index.json?astext=1" />
				</p>
			</blockquote>

			<div class="still-want">
				<a class="still-want-link" href="#section-form">Still want to die?</a>
			</div>
		</section>

		<section id="section-form" class="section_form">
			<form class="form-horizontal" data-ajax-url="/note?">
				<fieldset>
					<legend>Leave your note if you want</legend>
					<div class="control-group">
						<label for="from" class="control-label">From:</label>

						<div class="controls">
							<div class="input-append">
								<input id="from" class="span3" name="from" type="text" placeholder="Name, Last name">
								<span class="add-on"><i class="icon-user"></i></span>
							</div>
						</div>
					</div>
					<div class="control-group">
						<label for="to" class="control-label">To:</label>

						<div class="controls">
							<div class="input-append">
								<select id="to" class="focused span3" name="to">
									<option>Family</option>
									<option>Friends</option>
									<option>No one</option>
									<option>Board</option>

									<option value="type-name">Type name</option>
								</select>
								<span class="add-on"><i class="icon-heart"></i></span>
							</div>
						</div>
					</div>
					<div class="control-group">
						<label for="i-want-to-say" class="control-label">I want to say:</label>

						<div class="controls">
							<div class="input-append">
								<textarea id="i-want-to-say" class="span3" name="i-want-to-say" rows="6" ></textarea>
								<span class="add-on"><i class="icon-pencil"></i></span>
							</div>
						</div>
					</div>
					<div class="control-group">
						<label for="send-to" class="control-label">Send to:</label>

						<div class="controls">
							<div class="input-append">
								<input id="send-to" class="span3" name="send-to" type="email" placeholder="e-mail">
								<span class="add-on"><i class="icon-plus" data-max-emails="10"></i></span>
							</div>
						</div>
					</div>
					<div class="control-group">
						<label for="when" class="control-label">When:</label>

						<div class="controls">
							<div class="input-append">
								<input id="when" class="span3" name="when" type="date" placeholder="__/__/____ __:__">
								<span class="add-on"><i class="icon-calendar"></i></span>
								<input id="time-zone" name="time-zone" type="hidden">
							</div>
						</div>
					</div>

					<div class="form-actions">
						<button id="send-my-note" class="btn btn-danger" type="submit">Send my note</button>
					</div>
				</fieldset>
			</form>
		</section>

		<hr>

		<footer>
			<p>&copy; Suicide Note 2012</p>
		</footer>

	</div><!-- /container -->

	<div class="modal hide fade" id="successModal">
		<div class="modal-header">
			<a class="close" data-dismiss="modal">×</a>
			<h3>Be sure your note will be delivered as you asked...</h3>
		</div>
		<!--
		<div class="modal-body">
			<p></p>
		</div>

		<div class="modal-footer">
			<a class="btn" href="#" data-dismiss="modal">Close</a>
		</div>
		-->
	</div>

	<script src="//ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
	<script>window.jQuery || document.write('<script src="js/libs/jquery-1.7.1.min.js"><\/script>')</script>

	<script src="js/libs/jquery-ui-1.8.18/jquery-ui-1.8.18.min.js"></script>
	<script src="js/libs/jquery-ui-timepicker-addon.js"></script>
	<script src="js/libs/bootstrap/bootstrap-transition.js"></script>
	<script src="js/libs/bootstrap/bootstrap-modal.js"></script>
	<script src="js/libs/scrollTo.js"></script>
	<script src="js/libs/jquery.validate.min.js"></script>

	<script src="js/plugins.js"></script>
	<script src="js/script.js"></script>
	<script>
		var _gaq = _gaq || [];
		_gaq.push(['_setAccount', 'UA-16966225-3']);
		_gaq.push(['_trackPageview']);

		(function() {
			var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
			ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
			var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
		})();
	</script>
</body>
</html>