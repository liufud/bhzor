// Wait for the DOM to be ready
$(document).ready(function() {
  // Initialize form validation on the receivedOrderForm form.
  // It has the name attribute "receivedOrderForm"
  $("form[name='receivedOrderForm']").validate({
    // Specify validation rules
    rules: {
      // The key name on the left side is the name attribute
      // of an input field. Validation rules are defined
      // on the right side
      lotID:{
    	  required: true,
    	  digits: true,
    	  regex: /^[A-Za-z0-9_]+\@[A-Za-z0-9_]+\.[A-Za-z0-9_]+/,
      },
      qtyReceived: {
    	  required: true,
    	  digit: true,
    	  regex: /^[A-Za-z0-9_]+\@[A-Za-z0-9_]+\.[A-Za-z0-9_]+/,
      },
      qtyRejected: {
    	  digit: true,
    	  regex: /^[A-Za-z0-9_]+\@[A-Za-z0-9_]+\.[A-Za-z0-9_]+/,
      },
      expDate: {
        required: true,
        regex: /^(\d{1,2})\/(\d{1,2})\/(\d{2})$/,
      }
    },
    // Specify validation error messages
    messages: {
    	lotID:{
    		required:"Please specify the lot number",
    		regex:"Invalid lot number. Input has to be integers"
    	},
    	qtyReceived:{
    		required:"Please specify the quantity received",
    		regex:"Invalid quantity. Input has to be integers"
    	},
    	qtyRejected:{
    		regex:"Invalid quantity. Input has to be integers"
    	},
    	expDate:{
    		required:"Please specify the expiration date of this batch",
    		regex:"Invalid date format. Input has to be in format mm/dd/yy"
    	}
    },
    // Make sure the form is submitted to the destination defined
    // in the "action" attribute of the form when valid
    submitHandler: function(form) {
      form.submit();
    }
  });
});

$(document).ready.validator.addMethod(
        "regex",
        function(value, element, regexp) 
        {
            if (regexp.constructor != RegExp)
                regexp = new RegExp(regexp);
            else if (regexp.global)
                regexp.lastIndex = 0;
            return this.optional(element) || regexp.test(value);
        },
        "Please check your input."
);