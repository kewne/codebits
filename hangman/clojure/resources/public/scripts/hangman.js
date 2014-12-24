window.Hangman = Ember.Application.create();

// Routes

Hangman.Router.map(function() {
	this.route('word', { path: '/word/:id'});
	this.route('random', {path: '/word/random'});
	this.route('game-over');
	this.route('win');
});

Hangman.RandomRoute = Ember.Route.extend({
	model: function() {
		return Ember.$.getJSON('/services/word/random');
	},
	afterModel: function(word, transition) {
		this.transitionTo('word', word.id);
	}
});

Hangman.WordRoute = Ember.Route.extend({
	model: function(params) {
		return Ember.$.getJSON('/services/word/' + params.id).then(function(data) {
			var arr = [];
			for(var i = 0; i < data.length; i++) {
				arr.push('_');
			}
			return { id: data.id, word_letters: Ember.ArrayProxy.create({content: Ember.A(arr) }) };
		});
	},
	setupController: function(controller, model) {
		controller.set('tries', 10);
		controller.set('model', model);
	}
});

Hangman.WordController = Ember.ObjectController.extend({
	tries: 10,
	letters: ['A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'],
	rows: function() {
		return this.get('letters').reduce(function(p,c,i) {
			var row;
			if (i % 5 === 0) {
				row = { letters: [] };
				p.push(row);
			} else { row = p[Math.floor(i /5)]; }
			row.letters.push(c);
			return p;
		}, []);
	}.property('model.rows'),
	triesChanged: function() {
			if (this.get('tries') <= 0) {
				this.transitionTo('game-over');
			}
	}.observes('tries'),
	guessedWord: function() {
		var guess = this.get('word_letters').get('content').join(''),
			that = this;
		Ember.$.getJSON('/services/word/' + this.get('id') + '/guess', {guess: guess}, function(res) {
			if (res.correct) { that.transitionTo('win'); }
		});
	}.observes('word_letters.[]'),
	actions: {
		selectLetter: function(letter) {
				var that = this;
			Ember.$.getJSON('/services/word/' + that.get('id') + '/get_letter_ocurrences', {letter: letter})
				.then(function(ocurrences) {
					var letters = that.get('word_letters');
					if (ocurrences.length) {
						ocurrences.forEach(function(i) {
							letters.replace(i, 1, [letter]);
						});
					} else {
						that.decrementProperty('tries');
					}
				});
			}
		}
});

Hangman.LetterController = Ember.ObjectController.extend({
	needs: 'word',
	selected: false,
	actions: {
		selectLetter: function(letter) {
			this.set('selected', true);
			return true;
		}
	}
});
