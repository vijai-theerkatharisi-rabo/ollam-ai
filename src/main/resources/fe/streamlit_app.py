import requests
import streamlit as st

st.title("🦜🔗 My Guide")

# Function to identify and apply the appropriate formatting
def display_content_dynamically(content, response_area):
    # Check if the content looks like JSON or code
    if content.startswith("{") or content.startswith("["):
        # Use st.code for structured data like JSON or code
        response_area.code(content, language='json')  # You can specify language for syntax highlighting
    elif any(symbol in content for symbol in ['#', '*', '-', '**']):
        # Use st.markdown for markdown content
        response_area.markdown(content)
    else:
        # Default to st.write for plain text
        response_area.write(content)

def generate_response(input_text):
    response = requests.get("http://localhost:8080/chatting", params={"question": input_text}, stream=True)
    response_text = ""
    response_placeholder = st.empty()  # Create a placeholder for the response
    for chunk in response.iter_content(chunk_size=1024):
        if chunk:
            chunk_text = chunk.decode('utf-8').replace("data:", "").strip()
            response_text += chunk_text + " "
            formatted_text = format_response(response_text)
            display_content_dynamically(formatted_text,response_placeholder)  # Update the placeholder with the formatted response text

def format_response(text):
    
    # Split text into sentences
    sentences = text.split(". ")
    
    # Group sentences into paragraphs
    paragraphs = []
    paragraph = []
    for sentence in sentences:
        if sentence.strip():
            paragraph.append(sentence.strip() + ".")
        if len(paragraph) >= 3:  # Adjust the number of sentences per paragraph as needed
            paragraphs.append(" ".join(paragraph))
            paragraph = []
    if paragraph:
        paragraphs.append(" ".join(paragraph))
    
    # Join paragraphs with double newlines
    formatted_paragraphs = "\n\n".join(paragraphs)
    
    return formatted_paragraphs

with st.form("my_form"):
    text = st.text_area(
        "Enter text:",
        "What are the three key pieces of advice for learning how to code?",
    )
    submitted = st.form_submit_button("Submit")
    if submitted:
        generate_response(text)