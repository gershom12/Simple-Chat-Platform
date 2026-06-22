import { Injectable } from '@angular/core';
import { Client } from '@stomp/stompjs';
import SockJS from 'sockjs-client';
import { Subject } from 'rxjs';
import { APP_CONFIG } from '../config/app.config';

@Injectable({
  providedIn: 'root'
})
export class WebsocketService {

  private client!: Client;
  private messagesSubject = new Subject<any>();
  messages$ = this.messagesSubject.asObservable();

  connect(): void {

    this.client = new Client({
      webSocketFactory: () =>
        new SockJS(APP_CONFIG.WEBSOCKET_URL),

      reconnectDelay: 5000,

      debug: () => {}
    });

    this.client.onConnect = () => {
      this.client.subscribe(APP_CONFIG.CHAT_TOPIC, message => {
        this.messagesSubject.next(JSON.parse(message.body));
      });
    };

    this.client.activate();
  }

  disconnect(): void {
    this.client?.deactivate();
  }
}
