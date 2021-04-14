@tag
Feature: To test the compose functionality for Outlook

Background: Login to Outlook
Given User logs into Outlook with username and password 

	Scenario: Check Compose button
    Given User is on Outlook landing page
    When User clicks on Compose button
    Then User should be presented with a New Message frame
    
	Scenario: Empty recipient
		Given User is on Outlook landing page
		And User clicks on Compose button
		And User is on New Message frame
		And User clicks on Send button
		Then User should be prompted with "This message must have at least one recipient." error
   
	Scenario: Empty title and content
		Given User is on Outlook landing page
		And User clicks on Compose button
		And User is on New Message frame
		When User puts recipient of the email in "To" field 
		And User clicks on Send button
		Then User should get a "Missing subject" dialog
   
	Scenario: Check the subject of the message
		Given User is on Outlook landing page
		And User clicks on Compose button
		And User is on New Message frame
		And User reads the testdata from "EmailContents" sheet
		When User puts "Add a subject" of the email
		Then User should be able to see the given subject as title of the message
   
  @positive
  Scenario: Check the contents of email after send
		Given User is on Outlook landing page
   And User clicks on Compose button
   And User is on New Message frame
   And User reads the testdata from "EmailContents" sheet
   When User puts recipient of the email in "To" field
   And User puts "Add a subject" of the email	
   And User puts "Message body" of the email
   And User clicks on Send button
   Then User refreshes the page
   And User is on Outlook landing page
   And User should get the unread email with subject and body
   