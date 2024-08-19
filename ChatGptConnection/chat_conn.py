import openai
import os
from dotenv import load_dotenv

if __name__ == '__main__':

    load_dotenv()

    openai.api_key = os.getenv('CHAT_GPT_API_KEY')

    response = openai.chat.completions.create(
        model='gpt-3.5-turbo',
        response_format={'type': 'json_object'},
        messages=[
            {'role': 'system', 'content': 'java'}
        ]
    )

    output = response.choices[0].message.content

    print(output)