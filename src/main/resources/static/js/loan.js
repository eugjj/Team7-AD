function getValues()
{
	//button click gets values from inputs
	var carLoan = parseFloat(document.getElementById("carLoan").value);
	var interestRate = parseFloat(document.getElementById("interest").value/100.0);
	var years = parseInt(document.getElementById("years").value);
	
	//set the div string
	var div = document.getElementById("Result");
	
	//in case of a re-calc, clear out the div!
	div.innerHTML = "";
	
	//validate inputs - display error if invalid, otherwise, display table
	var balVal = validateInputs(carLoan);
	var intrVal = validateInputs(interestRate);

	if (balVal && intrVal)
	{
		//Returns div string if inputs are valid
		div.innerHTML += amort(carLoan, interestRate, years);
	}
	else
	{
		//returns error if inputs are invalid
		div.innerHTML += "Please Check your inputs and retry - invalid values.";
	}
}

/**
 * Amort function:
 * Calculates the necessary elements of the loan using the supplied user input
 * and then displays each months updated amortization schedule on the page
*/
function amort(carLoan, interestRate, years)
{
    //Calculate the per month interest rate
	
	
	//Calculate the payment
    var payment = (carLoan + (carLoan*interestRate*years))/(years*12);
	    
	//begin building the return string for the display of the amort table
    var result = "Monthly payment: $" + payment.toFixed(2) + "<br /><br />";
        
    
	
	//returns the concatenated string to the page
    return result;
}

function validateInputs(value)
{
	//some code here to validate inputs
	if ((value == null) || (value == ""))
	{
		return false;
	}
	else
	{
		return true;
	}
}