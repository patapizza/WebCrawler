<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>WebCrawler by BEST Team No. 1</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="webcrawler">
    <meta name="author" content="BEST Iasi 2012">


    <link href="/css/bootstrap.css" rel="stylesheet">
    <style>
      body {
        padding-top: 60px; /* 60px to make the container go all the way to the bottom of the topbar */
      }
    </style>
    <link href="/css/bootstrap-responsive.css" rel="stylesheet">

    <!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->

    <!-- Le fav and touch icons -->
    <link rel="shortcut icon" href="../assets/ico/favicon.ico">

  </head>

  <body>

    <div class="navbar navbar-fixed-top">
      <div class="navbar-inner">

        <div class="container">
          <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </a>
          <a class="brand" href="#">WebCrawler</a>
          <div class="nav-collapse">

            <ul class="nav">
              <li class="active"><a href="#">Home</a></li>
              <li><a href="#about">About</a></li>
              <li><a href="#contact">Contact</a></li>
            </ul>
          </div><!--/.nav-collapse -->
        </div>

      </div>
    </div>

    <div class="container">

      <h1 style="text-align: center; margin-top: 50px">Webcrawler frontend interface</h1>
      <p style="text-align: center">Please enter the desired query: </p>
      
      <form class="well form-search span4 offset4" style="margin-top: 50px" action="#" id="queryForm">
   			<input type="text" class="span3 search-query" id="query">
			  <button type="submit" class="btn" id="querySubmit">Search</button>
		  </form>

		  
		  <div class="well span8 offset2">
		  	<ul>
		  		<li>
			  		<a href="#" class="result-title"><strong>Test<strong> result</a>
			  		<div class="result-description">Testing result of the web page.</div>
			  		<a href="#" class="result-link">http://wwww.<strong>test</strong>page.com</a>
			  	</li>
		  	</ul>
		  </div>
		  
		  <div class="pagination span8 offset2">
        <ul>
          <li><a href="#">Prev</a></li>
          <li class="active">
          <a href="#">1</a>
			    </li>
			    <li><a href="#">2</a></li>
			    <li><a href="#">3</a></li>
			    <li><a href="#">4</a></li>
			   <li><a href="#">Next</a></li>
	  	 </ul>
		  </div>

    </div> <!-- /container -->

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
    
    <script src="/js/bootstrap-min.js"></script>
    <script src="/js/bootstrap-typeahead.js"></script>

    
    <script src="/js/script.js"></script>

  </body>
</html>
