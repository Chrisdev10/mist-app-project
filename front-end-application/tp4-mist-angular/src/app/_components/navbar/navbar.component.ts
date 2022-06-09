import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/_services/auth.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit {

  constructor(
    public authService: AuthService,
    private router: Router
  ) { }

  ngOnInit(): void {
  }

  onClick(event: any): void {
    this.router.navigate([event.target.id]);
  }

  onSignIn(): void {
    this.router.navigate(['auth', 'sign-in']);
  }

  onSignOut(): void {
    this.authService.signOut();
  }

}
