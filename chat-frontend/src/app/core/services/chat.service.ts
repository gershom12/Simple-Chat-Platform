import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { APP_CONFIG } from '../config/app.config';
import { WebsocketService } from './websocket.service';

@Injectable({
  providedIn: 'root'
})
export class ChatService {

  constructor(
    private http: HttpClient,
    private websocketService: WebsocketService
  ) {}

  join(username: string) {

    return this.http.post(
      `${APP_CONFIG.API_BASE_URL}/join`,
      { username }
    );
  }

  sendMessage(
    username: string,
    content: string
  ) {

    return this.http.post(
      `${APP_CONFIG.API_BASE_URL}/send`,
      {
        username,
        content
      }
    );
  }

  connect() {

    this.websocketService.connect();
  }

  getMessages() {

    return this.websocketService.messages$;
  }
}