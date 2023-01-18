class Mark:
    def __init__(self, subject: str, mark: int):
        self.subject = subject
        self.mark = mark
    def __repr__(self):
        return self.subject+' '+ str(self.mark)