Feature: To Test Basic

Scenario: To login amazon
	Given the user opens the amazon url
	When the user searches for the product
	And the product result appears
	And the user clicks on the product
	And the user adds the product to the cart
	Then the login page appears