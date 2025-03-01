# Influencer Ranking Web Application

This web application allows users to view and manage a list of influencers, categorized by their follower count, and filter them based on key engagement metrics like interactions, followers, and more.

## Features

- **Categorization of Influencers**: 
  - The app categorizes influencers into three groups:
    - **Nano Influencers**: 1k - 10k followers
    - **Micro Influencers**: 10k - 100k followers
    - **Macro Influencers**: 100k+ followers
  
- **Sorting**: 
  - Influencers can be sorted by:
    - Followers count
    - Average engagement metrics (likes, shares, comments)
  
- **Detailed View**: 
  - Each influencer’s detailed statistics, including their location and campaign participation, can be viewed with a simple click.

- **Invite to Campaign**: 
  - Users can send invitations to influencers to join a campaign with a dedicated button and popup notification.

## Technology Stack

- **Frontend**: Scala Play Framework (with HTML and CSS for layout and design)
- **Backend**: Scala with Slick for database interaction
- **Database**: SQL-based database (assumed for storing influencer data)
