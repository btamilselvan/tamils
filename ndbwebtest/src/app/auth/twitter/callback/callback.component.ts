import { Component, OnInit } from '@angular/core';
import {  ActivatedRoute } from '@angular/router';
import { SocialAuthService } from '../../social-auth/social-auth.service';

@Component({
  selector: "app-twitter-callback",
  template: `
    <p>
      callback works!
    </p>
  `,
  styles: []
})
export class CallbackComponent implements OnInit {

  constructor(private route: ActivatedRoute, private socialService: SocialAuthService) { }

  ngOnInit() {
    //step 2 response...
      console.log('oauth_token1', this.route.snapshot.queryParamMap.get("oauth_token"));
      console.log('oauth_verifier1', this.route.snapshot.queryParamMap.get("oauth_verifier"));
      const token = this.route.snapshot.queryParamMap.get("oauth_token");
      const verifier = this.route.snapshot.queryParamMap.get("oauth_verifier");
      const token_secret = sessionStorage.getItem('oauth_secret');
      //step 3 send oauth token and verifier to backend to link account...
      this.socialService.linkTwitterAccount(token, token_secret, verifier).subscribe(
        data=> {
          console.log('step 3 response', data);
        }
      );
  }

}
