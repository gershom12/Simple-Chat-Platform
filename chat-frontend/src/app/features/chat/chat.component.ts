import { Component, OnInit } from '@angular/core';
import { ChatService } from '../../core/services/chat.service';
import { ChatMessage } from '../../models/chat-message.model';

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css']
})
export class ChatComponent implements OnInit {

  username = '';

  message = '';

  joined = false;

  messages: ChatMessage[] = [];

  constructor(
    private chatService: ChatService
  ) {}

  ngOnInit(): void {

    this.chatService.connect();

    this.chatService
      .getMessages()
      .subscribe(msg => {

        this.messages.push(msg);
      });
  }

  joinChat(): void {

    this.chatService
      .join(this.username)
      .subscribe(() => {

        this.joined = true;
      });
  }

  sendMessage(): void {

    if (!this.message.trim()) {
      return;
    }

    this.chatService
      .sendMessage(
        this.username,
        this.message
      )
      .subscribe();

    this.message = '';
  }
}