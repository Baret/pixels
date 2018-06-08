$('document').ready(function(){
	// initialize colorpickers
	$('.colorpicker-component').colorpicker({
	      useAlpha: false,
	      fallbackColor: this.getElementsByTagName('input')[0].value,
	      component: '.badge'
    }).on('changeColor', function(e) {
		e.target.getElementsByClassName('badge')[0].innerHTML = e.color.toHex();
    });

	// toggle visibility of input fields
	$("#togglebutton").click(function() {
	    $(".form-control").toggle();
	});

	// ajax call

	$('#updateForm').submit(function(event) {

        //  event.preventDefault();

		var pixelValues = new Map();
		$('.pixel').
			foreach(function() {
				pixelValues.set(this.id(), this.val());
				});
		console.log("PixelValues: " + pixelValues);
		var json = { "pixels" : pixelValues};

		$.ajax({
		    url: $("#updateForm").attr("action"),
		    data: JSON.stringify(json),
		    dataType: 'json',
		    type: "POST",
		    contentType: "application/json",

		    /*beforeSend: function(xhr) {
		        xhr.setRequestHeader("Accept", "application/json");
		        xhr.setRequestHeader("Content-Type", "application/json");
		    },*/
		    success: function() {
		        console.log("yay, saved!");
		    }
		});
    });
});